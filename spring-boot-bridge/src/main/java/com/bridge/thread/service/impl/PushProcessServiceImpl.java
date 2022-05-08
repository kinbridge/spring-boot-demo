package com.bridge.thread.service.impl;

import com.bridge.thread.service.PushProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bridge
 * @Date 2022/04/22/23:24
 */
@Slf4j
@Service
public class PushProcessServiceImpl implements PushProcessService {
    @Autowired
    private PushUtil pushUtil;
    @Autowired
    private PushProcessMapper pushProcessMapper;

    private final static Logger logger = LoggerFactory.getLogger(PushProcessServiceImpl.class);

    //每个线程每次查询的条数
    private static final Integer LIMIT = 5000;
    //起的线程数
    private static final Integer THREAD_NUM = 5;
    //创建线程池
    ThreadPoolExecutor pool = new ThreadPoolExecutor(THREAD_NUM, THREAD_NUM * 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));

    @Override
    public void pushData() throws ExecutionException, InterruptedException {
        //计数器，需要保证线程安全
        int count = 0;
        //未推送数据总数
        Integer total = pushProcessMapper.countPushRecordsByState(0);
        logger.info("未推送数据条数：{}", total);
        //计算需要多少轮
        int num = total / (LIMIT * THREAD_NUM) + 1;
        logger.info("要经过的轮数:{}", num);
        //统计总共推送成功的数据条数
        int totalSuccessCount = 0;
        for (int i = 0; i < num; i++) {
            //接收线程返回结果
            List<Future<Integer>> futureList = new ArrayList<>(32);
            //起THREAD_NUM个线程并行查询更新库，加锁
            for (int j = 0; j < THREAD_NUM; j++) {
                synchronized (PushProcessServiceImpl.class) {
                    int start = count * LIMIT;
                    count++;
                    //提交线程，用数据起始位置标识线程
                    Future<Integer> future = pool.submit(new PushDataTask(start, LIMIT, start));
                    //先不取值，防止阻塞,放进集合
                    futureList.add(future);
                }
            }
            //统计本轮推送成功数据
            for (Future f : futureList) {
                totalSuccessCount = totalSuccessCount + (int) f.get();
            }
        }
        //更新推送标志
        pushProcessMapper.updateAllState(1);
        logger.info("推送数据完成，需推送数据:{},推送成功：{}", total, totalSuccessCount);
    }
