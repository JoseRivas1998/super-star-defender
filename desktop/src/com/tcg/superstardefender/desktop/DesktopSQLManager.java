package com.tcg.superstardefender.desktop;

import com.tcg.superstardefender.Game;
import com.tcg.superstardefender.managers.DatabaseManager;
import java.sql.*;

public class DesktopSQLManager implements DatabaseManager {


	
	private Connection sqlCon;
	private Statement sqlState;
	
	@Override
	public void mergeScore(int[] highscores) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean login(String username, String password) {
		boolean userMatch, passwordMatch;
		try{
			sqlCon = DriverManager.getConnection(dbURL, dbUSER, dbPASSWORD);
			sqlState = sqlCon.createStatement();
			ResultSet result = sqlState.executeQuery("SELECT username FROM users ORDER BY id");
			while(result.next()) {
				if(username.equals(result.getString("username"))) {
					userMatch = true;
					ResultSet useid = sqlState.executeQuery("SELECT id FROM users WHERE username = '" + username + "'");
					while(useid.next()) {
						int id = new Integer(useid.getString("id").trim()).intValue();
						Game.USER_ID = id;
						ResultSet passid = sqlState.executeQuery("SELECT password FROM users WHERE id = " + Game.USER_ID);
						while(passid.next()) {
							passwordMatch = password.equals(passid.getString("password"));
							return userMatch && passwordMatch;
						}
					}
				}
			}
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			userMatch = false;
			passwordMatch = false;
			return false;
		}
	}

}
