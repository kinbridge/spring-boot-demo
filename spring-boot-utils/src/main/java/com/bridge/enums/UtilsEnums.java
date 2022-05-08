package com.bridge.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bridge
 * @Date 2022/05/08/18:17
 */
@Getter
@AllArgsConstructor
public enum UtilsEnums {

    CHAPTER_TITLE_REGEX("^\\S\\d章[\\s\\S]+$","章节正则表达式"),
    FIRST_TITLE_REGEX("^\\d{1,2}.\\d[\\s\\S]+$","章节正则表达式")
    ;

    private String code;

    private String desc;
}
