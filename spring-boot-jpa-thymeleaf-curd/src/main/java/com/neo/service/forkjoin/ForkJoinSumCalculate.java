package com.neo.service.forkjoin;

import com.neo.model.User;
import com.neo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @author bridge
 * @Date 2022/01/03/19:15
 */
@Service
public class ForkJoinSumCalculate extends RecursiveTask<Integer> {

    @Autowired
    private UserRepository userRepository;

    private int start;
    private int end;
    private List<User> list;
    int sum = 0;
    private static final int THRESHOLD = 20000; //临界值

    public ForkJoinSumCalculate(int start, int end, List<User> list) {
        this.start = start;
        this.end = end;
        this.list = list;
    }

    @Override
    protected Integer compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            //在临界值范围内，实现业务代码
            List<User> temList = new ArrayList<>();
            for (int i = start; i < end; i++) {
                temList.add(list.get(i));
            }
            //入库操作
            userRepository.saveAll(temList);
            sum++;
            return sum;
        } else {
            //不在临界值范围，继续拆分
            int middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle, list);
            left.fork();
            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end, list);
            right.fork();
            int l = left.join() + right.join();
            return l;
        }
    }

    public void importData() {
        List<User> rowList = new ArrayList();
        for (int i = 1; i < 100 * 100 * 100; i++) {
            User u = new User();
            u.setAge(18);
            u.setUserName("test" + i);
            u.setPassword("pwd" + i);
            rowList.add(u);
        }
        //到此成功取出Excel中每行第一列与第四列数据
        long begin = System.currentTimeMillis();
        ForkJoinSumCalculate fjc = new ForkJoinSumCalculate(0, rowList.size(), rowList);
        Integer compute = fjc.compute();
        long end = System.currentTimeMillis();
        System.out.println("总时间:" + (end - begin));
        System.out.println("总长度" + rowList.size());
        System.out.println("总次数" + compute);
    }

}

