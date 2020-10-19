package cn.jarod.bluecat.auth.service;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/4/23
 */
public class MapDemoTest {

    @Test
    void more_than_one_map_merge(){
        Map<String,Object> h1 = ImmutableMap.<String,Object>builder()
                .put("H1","fdsa")
                .put("H2","fdsa")
                .put("H3","fdsa")
                .put("H4","fdsa")
                .build();

        Map<String,Object> h2 = ImmutableMap.<String,Object>builder()
                .put("H1","fdsa1")
                .put("H2","fdsa1")
                .put("H3","fdsa1")
                .put("H5","fdsa1")
                .build();

        Map<String,Object> h3 = ImmutableMap.<String,Object>builder()
                .put("H1","fdsa2")
                .put("H2","fdsa2")
                .put("H3","fdsa2")
                .put("H6","fdsa2")
                .build();

        List<Map<String,Object>> lists = new ArrayList<>();
        lists.add(h1);
        lists.add(h2);
        lists.add(h3);

        Map map = lists.stream().flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)->a));
        assertEquals(6,map.size());
        assertEquals("fdsa",map.get("H1"));
    }
}
