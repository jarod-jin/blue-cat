package cn.jarod.bluecat.core.utils;


import cn.jarod.bluecat.core.model.TreeBO;
import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @auther jarod.jin 2019/9/4
 */
public class TreeUtil {


    /**
     * 获取一级节点
     * @param elements
     * @return
     */
    public static List<TreeBO> getTree(List<? extends TreeBO> elements) {
        List<TreeBO> baseLists = Lists.newArrayList();
        // 总菜单，出一级菜单，一级菜单没有父节点
        for (TreeBO e: elements) {
            if (StringUtils.isEmpty(e.getPNode())) {
                baseLists.add(e);
            }
        }
        // 遍历一级菜单
        for (TreeBO m : baseLists) {
            // 将子元素 set进一级菜单里面
            m.setChildren(getChild(m.getNode(),elements) );
        }
        return baseLists;
    }

    /**
     * 获取子节点
     * @param pNode
     * @param elements
     * @return
     */
    private static List<TreeBO> getChild(String pNode, List<? extends TreeBO> elements) {
        List<TreeBO> children = Lists.newArrayList();
        for (TreeBO e: elements) {
            if(StringUtils.hasText(e.getPNode()) && e.getPNode().equals(pNode)){
                // 子菜单的下级菜单
                children.add(e);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (TreeBO m: children) {
            // 继续添加子元素
            m.setChildren(getChild(m.getNode(), elements));
        }
        //停下来的条件，如果 没有子元素了，则停下来
        return children.isEmpty() ? null : children;
    }
}
