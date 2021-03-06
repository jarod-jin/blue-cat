package cn.jarod.bluecat.core.common.utils;


import cn.jarod.bluecat.core.api.pojo.TreeDO;
import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @author jarod.jin 2019/9/4
 */
public class TreeUtil {


    /**
     * 获取一级节点
     * @param elements
     * @return
     */
    public static List<TreeDO> getTree(List<? extends TreeDO> elements) {
        List<TreeDO> baseLists = Lists.newArrayList();
        // 总菜单，出一级菜单，一级菜单没有父节点
        for (TreeDO e: elements) {
            if (StringUtils.isEmpty(e.getParentId())) {
                baseLists.add(e);
            }
        }
        // 遍历一级菜单
        for (TreeDO m : baseLists) {
            // 将子元素 set进一级菜单里面
            m.setChildren(getChild(m.getNodeId(),elements) );
        }
        return baseLists;
    }

    /**
     * 获取子节点
     * @param pNode
     * @param elements
     * @return
     */
    private static List<TreeDO> getChild(String pNode, List<? extends TreeDO> elements) {
        List<TreeDO> children = Lists.newArrayList();
        for (TreeDO e: elements) {
            if(StringUtils.hasText(e.getParentId()) && e.getParentId().equals(pNode)){
                // 子菜单的下级菜单
                children.add(e);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (TreeDO m: children) {
            // 继续添加子元素
            m.setChildren(getChild(m.getNodeId(), elements));
        }
        //停下来的条件，如果 没有子元素了，则停下来
        return children.isEmpty() ? null : children;
    }
}
