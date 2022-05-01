package com.database.mysql.actions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActionUtilities {
	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(ActionUtilities.class);

	public static boolean validateUser(String username, String password) {
		String sqlQuery = "select * from service_users where user_id='" + username + "' and user_pass='" + password
				+ "'";
		ResultSet resultSet = null;
		boolean hasNext = false;
		try {
			Statement statement = SqlConstants.getStatement();
			resultSet = statement == null ? null : statement.executeQuery(sqlQuery);
			hasNext = resultSet == null ? false : resultSet.next();
		} catch (SQLException e) {
			System.out.println(e + " ERROR in validateUser");
			LOGGER.error(e.getLocalizedMessage());
			return false;
		} finally {
			SqlConstants.closeConnection();
		}

		return resultSet != null ? hasNext : false;
	}

	public static ResultSet getGroupNames(String username) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT list.list_name");
		sqlQuery.append(" FROM list");
		sqlQuery.append(" INNER JOIN service_users ON list.user_key=service_users.user_key");
		sqlQuery.append(" WHERE service_users.user_name='");
		sqlQuery.append(username);
		sqlQuery.append("' AND list.status=0");
		ResultSet resultSet = null;
		try {
			Statement statement = SqlConstants.getStatement();

			resultSet = statement.executeQuery(sqlQuery.toString());
		} catch (SQLException e) {
			System.out.println(e + " ERROR in getGroupNames");
			LOGGER.error(e.getLocalizedMessage());
		}

		return resultSet;
	}

	public static ResultSet getGroupNotes(String groupName, String username) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT todo.todo_note, todo.selected");
		sqlQuery.append(" FROM todo");
		sqlQuery.append(" INNER JOIN list ON todo.list_key=list.list_key");
		sqlQuery.append(" INNER JOIN service_users ON list.user_key=service_users.user_key");
		sqlQuery.append(" WHERE list.list_name= '");
		sqlQuery.append(groupName);
		sqlQuery.append("' AND service_users.user_name='");
		sqlQuery.append(username);
		sqlQuery.append("' AND todo.status=0;");
		ResultSet resultSet = null;
		try {
			Statement statement = SqlConstants.getStatement();

			resultSet = statement.executeQuery(sqlQuery.toString());
		} catch (SQLException e) {
			System.out.println(e + " ERROR in getGroupNotes");
			LOGGER.error(e.getLocalizedMessage());
		}

		return resultSet;
	}

	public static int saveNote(String group, String note, String username) {
		StringBuilder sqlQuery = new StringBuilder();

		String listKey = getListKey(group, username);
		sqlQuery.append("INSERT INTO todo (todo_note, status, list_key)");
		sqlQuery.append(" VALUES ('");
		sqlQuery.append(note);
		sqlQuery.append("', ");
		sqlQuery.append("0");
		sqlQuery.append(", ");
		sqlQuery.append(listKey);
		sqlQuery.append(");");
		int resultSet = 0;
		if (listKey != null) {
			try {
				Statement statement = SqlConstants.getStatement();
				resultSet = statement.executeUpdate(sqlQuery.toString());
			} catch (SQLException e) {
				System.out.println(e + " ERROR in saveNote");
				LOGGER.error(e.getLocalizedMessage());
			} finally {
				SqlConstants.closeConnection();
			}
		}

		return resultSet == 0 ? 0 : 1;
	}

	private static String getListKey(String group, String username) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select list_key from list");
		sqlQuery.append(" INNER JOIN service_users ON list.user_key=service_users.user_key");
		sqlQuery.append(" where list_name ='");
		sqlQuery.append(group);
		sqlQuery.append("' AND service_users.user_name='");
		sqlQuery.append(username);
		sqlQuery.append("';");
		ResultSet resultSet = null;
		try {
			Statement statement = SqlConstants.getStatement();

			resultSet = statement.executeQuery(sqlQuery.toString());
			if (resultSet != null && resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			System.out.println(e + " ERROR in getGroupid");
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			SqlConstants.closeConnection();
		}
		return null;
	}

	public static int deleteNote(String group, String note, String username) {
		StringBuilder sqlQuery = new StringBuilder();

		String listKey = getListKey(group, username);
		sqlQuery.append("UPDATE todo");
		sqlQuery.append(" SET status = B'1'");
		sqlQuery.append(" WHERE todo_note = '");
		sqlQuery.append(note);
		sqlQuery.append("' AND status = 0");
		sqlQuery.append(" AND  list_key = ");
		sqlQuery.append(listKey);
		sqlQuery.append(";");
		int resultSet = 0;
		if (listKey != null) {
			try {
				Statement statement = SqlConstants.getStatement();

				resultSet = statement.executeUpdate(sqlQuery.toString());
			} catch (SQLException e) {
				System.out.println(e + " ERROR in deleteNote");
				LOGGER.error(e.getLocalizedMessage());
			} finally {
				SqlConstants.closeConnection();
			}
		}

		return resultSet == 0 ? 0 : 1;
	}

	public static int checkNote(String group, String note, String isChecked, String username) {
		StringBuilder sqlQuery = new StringBuilder();

		String listKey = getListKey(group, username);
		sqlQuery.append("UPDATE todo");
		sqlQuery.append(" SET selected =");
		sqlQuery.append("y".equals(isChecked) ? "B'1'" : 0);
		sqlQuery.append(" WHERE todo_note = '");
		sqlQuery.append(note);
		sqlQuery.append("' AND status=0");
		sqlQuery.append(" AND  list_key = ");
		sqlQuery.append(listKey);
		sqlQuery.append(";");
		int resultSet = 0;
		if (listKey != null) {
			try {
				Statement statement = SqlConstants.getStatement();

				resultSet = statement.executeUpdate(sqlQuery.toString());
			} catch (SQLException e) {
				System.out.println(e + " ERROR in deleteNote");
				LOGGER.error(e.getLocalizedMessage());
			} finally {
				SqlConstants.closeConnection();
			}
		}

		return resultSet == 0 ? 0 : 1;
	}

	public static int deleteGroup(String group) {
		StringBuilder sqlQuery = new StringBuilder();

		sqlQuery.append("UPDATE list");
		sqlQuery.append(" SET status = B'1'");
		sqlQuery.append(" WHERE list_name = '");
		sqlQuery.append(group);
		sqlQuery.append("' AND status=0");
		sqlQuery.append(";");
		int resultSet = 0;

		try {
			Statement statement = SqlConstants.getStatement();

			resultSet = statement.executeUpdate(sqlQuery.toString());
		} catch (SQLException e) {
			System.out.println(e + " ERROR in deleteGroup");
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			SqlConstants.closeConnection();
		}

		return resultSet == 0 ? 0 : 1;
	}

	public static int createNewGroup(String group, String user) {
		StringBuilder sqlQuery = new StringBuilder();
		String userKey = getUserKey(user);

		sqlQuery.append("INSERT INTO list");
		sqlQuery.append(" (list_name, user_key, status)");
		sqlQuery.append(" VALUES ('");
		sqlQuery.append(group);
		sqlQuery.append("', ");
		sqlQuery.append(userKey);
		sqlQuery.append(", 0);");
		int resultSet = 0;
		if (userKey != null) {
			try {
				Statement statement = SqlConstants.getStatement();

				resultSet = statement.executeUpdate(sqlQuery.toString());
			} catch (SQLException e) {
				System.out.println(e + " ERROR in createNewGroup");
				LOGGER.error(e.getLocalizedMessage());
			} finally {
				SqlConstants.closeConnection();
			}
		}

		return resultSet == 0 ? 0 : 1;
	}

	/**
	 * returns user key
	 * @param user
	 * @return
	 */
	private static String getUserKey(String user) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select user_key from service_users");
		sqlQuery.append(" where user_name ='");
		sqlQuery.append(user);
		sqlQuery.append("';");
		ResultSet resultSet = null;
		try {
			Statement statement = SqlConstants.getStatement();

			resultSet = statement.executeQuery(sqlQuery.toString());
			if (resultSet != null && resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			System.out.println(e + " ERROR in getUserId");
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			SqlConstants.closeConnection();
		}

		return null;
	}
	
	/**
	 * 
	 * @param userName
	 * @param userId
	 * @param userPass
	 * @return
	 */
	public static int createUser(String userName, String userId, String userPass) {
		StringBuilder sqlQuery = new StringBuilder();

		sqlQuery.append("INSERT INTO service_users (user_name, user_id, user_pass)");
		sqlQuery.append(" VALUES ('");
		sqlQuery.append(userName);
		sqlQuery.append("', ");
		sqlQuery.append(userId);
		sqlQuery.append(", ");
		sqlQuery.append(userPass);
		sqlQuery.append(");");
		int resultSet = 0;
			try {
				Statement statement = SqlConstants.getStatement();
				resultSet = statement.executeUpdate(sqlQuery.toString());
			} catch (SQLException e) {
				System.out.println(e + " ERROR in saveNote");
				LOGGER.error(e.getLocalizedMessage());
			} finally {
				SqlConstants.closeConnection();
			}

		return resultSet == 0 ? 0 : 1;
	}
}
