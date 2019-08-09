package com.newlandpay.newretail.appstore.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页对象
 * @param <T>
 * @author chenkai
 */

@Getter
@Setter
@Builder
public class PageEntity<T> {

    private Integer pageNo;
    private Integer pageSize;
    @Builder.Default
    private Long total=0L;
    private List<T> rows;
}
