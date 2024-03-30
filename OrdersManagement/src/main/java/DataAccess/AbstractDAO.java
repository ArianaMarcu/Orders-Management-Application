package DataAccess;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import Connection.ConnectionFactory;
/**
 * The AbstractDAO class is a generic data access object that provides common CRUD operations for entities.
 *
 * @param <T> the type of entity
 */
public class AbstractDAO<T>
{
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;
    /**
     * Constructs a new AbstractDAO instance.
     */
    public AbstractDAO()
    {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    /**
 * Creates a SELECT query with a specified field.
 *
 * @param field the field to select
 * @return the SELECT query as a string
 */
    private String createSelectQuery(String field)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    /**
     * Creates a SELECT query to retrieve all entities.
     *
     * @return the SELECT query as a string
     */
    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("* ");
        sb.append("FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }
    /**
     * Finds and retrieves all entities of type T.
     *
     * @return a list of entities
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String string = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(string);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:find " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * Finds and retrieves an entity of type T by its ID.
     *
     * @param id the ID of the entity to find
     * @return the found entity or null if not found
     */
    public T findById(int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.init();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * Creates objects of type T from the given ResultSet.
     *
     * @param resultSet the ResultSet containing the data
     * @return a list of created objects
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * Creates an INSERT query for inserting an entity of type T.
     *
     * @return the INSERT query as a string
     */
    private String createInsertQuery() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");
        for (Field f : type.getDeclaredFields()) {
            if (!f.equals(type.getDeclaredFields()[0]))
                sb.append(f.getName() + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUES (");
        for (Field f : type.getDeclaredFields()) {
            if (!f.equals(type.getDeclaredFields()[0])) {
                if(!f.equals(type.getDeclaredFields()[1])){
                    sb.append(",");
                }
                sb.append("?");
            }
        }
        sb.append(")");
        System.out.println(sb);
        return sb.toString();
    }
    /**
     * Inserts a new entity into the database.
     *
     * @param t the entity to insert
     * @return the inserted entity or null if insertion fails
     */
    public T insert(T t)
    {
        Connection dbConnection = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement insertStatement = null;
        int i = 1;
        try
        {
            insertStatement = dbConnection.prepareStatement(createInsertQuery(), Statement.RETURN_GENERATED_KEYS);
            for (Field f : type.getDeclaredFields()) {
                if (!f.equals(type.getDeclaredFields()[0])) {
                    f.setAccessible(true);
                    Object obj = f.get(t);
                    insertStatement.setString(i, obj.toString());
                    i++;
                }
            }
            insertStatement.executeUpdate();
            rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                Field f = type.getDeclaredField("id");
                f.setAccessible(true);
                f.set(t, id);
            }
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:insert " + e.getMessage());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return null;
    }
    /**
     * Creates an UPDATE query for updating an entity of type T.
     *
     * @return the UPDATE query as a string
     */
    private String createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for (Field f : type.getDeclaredFields()) {
            if (!f.equals(type.getDeclaredFields()[0])) {
                sb.append(f.getName());
                sb.append("=?, ");
            }
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append(" WHERE ");
        sb.append("id = ?");
        return sb.toString();
    }
    /**
     * Updates an existing entity in the database.
     *
     * @param t  the entity to update
     * @param id the ID of the entity to update
     * @return the updated entity or null if update fails
     */
    public T update(T t, int id)
    {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        int i = 1;
        try {
            updateStatement = dbConnection.prepareStatement(createUpdateQuery(), Statement.NO_GENERATED_KEYS);
            for (Field f : type.getDeclaredFields()) {
                if (!f.equals(type.getDeclaredFields()[0])) {
                    f.setAccessible(true);
                    Object obj = f.get(t);
                    updateStatement.setString(i, obj.toString());
                    i++;
                }
            }
            updateStatement.setString(i, String.valueOf(id));
            updateStatement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:update " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
        return null;
    }
}
