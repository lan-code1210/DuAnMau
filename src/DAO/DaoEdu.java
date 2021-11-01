
package DAO;

import java.util.List;

public abstract class DaoEdu<Entity, Ken>{
    abstract public void insert(Entity e);
    abstract public void update(Entity e);
    abstract public void delete(Ken k);
    abstract public List<Entity> selectAll();
    abstract public Entity selectById(Ken k);
    abstract protected List<Entity> selectBySQL(String sql, Object... args);
}
