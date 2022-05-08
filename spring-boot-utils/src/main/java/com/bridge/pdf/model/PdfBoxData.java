package com.bridge.pdf.model;

import lombok.*;

/**
 * @author bridge
 * @Date 2022/05/08/23:14
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PdfBoxData {
    /**
     * 标题
     */
    private String title;

    /**
     * 页码
     */
    private int page;

}