package com.egolik.taxi.dao;

public class DAOFactory {
    private static IMyDAO dao = null;

    public static IMyDAO getDAOInstance(TypeDAO type) {
        if(type == TypeDAO.MySQL){
            if(dao == null){
                dao = new MySQLDAO();
                return dao;
            }
            else return dao;
        }
        return null;
    }

}
