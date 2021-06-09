package com.mng.bean.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderGraphResponse {
    private List<Integer> names;
    private List<Double> values;
}
