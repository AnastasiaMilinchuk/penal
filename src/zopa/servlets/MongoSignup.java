package zopa.servlets;

import com.mongodb.*;
import sun.nio.cs.Surrogate;
import zopa.controllers.ConnectionToDataBase;
import zopa.controllers.MongoDBController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by milinchuk on 4/28/14.
 */
public class MongoSignup extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       /* MongoClient mongoClient = MongoDBController.getMongoDBClient("localhost");
        DB socialNetwork = MongoDBController.getMongoDataBase(mongoClient, "Social_Network");
        boolean auth = socialNetwork.authenticate("milinchuk","1111".toCharArray());
*/
        ConnectionToDataBase.set("localhost", "Social_Network");
        DBCollection user = MongoDBController.getMongoDBCollection(ConnectionToDataBase.get(), "users");

        BasicDBObject data = new BasicDBObject("login", request.getParameter("email").toString())
                .append("password", request.getParameter("password").toString());
        DBCursor cursor= user.find(data);
        boolean isExist = false;
        DBObject userData = null;

        while (cursor.hasNext()){
            isExist = true;
            userData = cursor.next();
        }

        if(userData != null){
            request.getSession().setAttribute("name", (userData.get("firstname").toString() + " " + userData.get("secondname").toString()));
            request.getSession().setAttribute("email", request.getParameter("email"));
            request.getSession().setAttribute("birthday", userData.get("birthday").toString());
            request.getSession().setAttribute("phone", userData.get("phone"));
            request.getSession().setAttribute("education", userData.get("education"));

            request.getSession().setAttribute("login", request.getParameter("email").toString());
            //request.getSession().setAttribute("password", request.getParameter("password"));*/
            request.getSession().setAttribute("invalid", "");
            request.getRequestDispatcher("/profile1.jsp").forward(request,response);
        }
        else{
            request.getSession().setAttribute("invalid", "Invalid login or password");
            request.getSession().setAttribute("user", "false");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       // request.getRequestDispatcher("/signup.jsp").forward(request, response);
    }
}
