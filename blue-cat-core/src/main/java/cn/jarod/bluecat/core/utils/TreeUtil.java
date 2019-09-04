package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.model.TreeVO;
import com.google.common.collect.Lists;

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
    public static List<TreeVO> getTree(List<? extends TreeVO> elements) {
        List<TreeVO> baseLists = Lists.newArrayList();
        // 总菜单，出一级菜单，一级菜单没有父id
        for (TreeVO e: elements) {
            if (e.getParentId() == null || e.getParentId()==0 ) {
                baseLists.add(e);
            }
        }
        // 遍历一级菜单
        for (TreeVO m : baseLists) {
            // 将子元素 set进一级菜单里面
            m.setChildren(getChild(m.getId(),elements) );
        }
        return baseLists;
    }

    /**
     * 获取子节点
     * @param pid
     * @param elements
     * @return
     */
    private static List<TreeVO> getChild(Long pid, List<? extends TreeVO> elements) {
        List<TreeVO> children = Lists.newArrayList();
        for (TreeVO e: elements) {
            if(e.getParentId() != null && e.getParentId().equals(pid)){
                // 子菜单的下级菜单
                children.add(e);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (TreeVO m: children) {
            // 继续添加子元素
            m.setChildren(getChild(m.getId(), elements));
        }
        //停下来的条件，如果 没有子元素了，则停下来
        return children.isEmpty() ? null : children;
    }
}
