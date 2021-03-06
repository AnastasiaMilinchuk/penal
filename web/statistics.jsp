<%@ page import="findwith.DAORealizations.StatisticImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="findwith.Entities.Skill" %>
<%@ page import="findwith.Entities.Industry" %>
<%@ page import="findwith.Entities.TopCountry" %>
<%--
  Created by IntelliJ IDEA.
  User: milinchuk
  Date: 12/14/14
  Time: 8:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script src="javascript/jquery.js"></script>
    <script src="javascript/add-skill-to-find.js"></script>
    <script src="javascript/country.js"></script>
    <script src="javascript/city.js"></script>
    <script src="javascript/industry.js"></script>
    <script src="javascript/find-people.js"></script>
    <script src="javascript/delete-skill-to-find.js"></script>
    <script src="javascript/add-to-friends.js"></script>

</head>
<body>
<jsp:include page="upperBox.jsp"/>

<section class="statistic-section">
    <h1 class="statistic-search-type">Statistics</h1>
    <div class="statistic-info">
        <h2>Top-10 skills</h2>
        <%
            StatisticImpl statistic = new StatisticImpl();
            List<Skill> skills = statistic.getTopSkills();
            int size = skills.size();
            if(size >= 10){
                size = 10;
            }
            out.print("<table style='padding-left: 30%;' cellpadding='5px;'>");
            out.print("<thead>");
            out.print("<th>Skill</th>");
            out.print("<th style='text-align: center;'>Popularity</th>");
            out.print("<th style='text-align: center;'>Average age</th>");
            out.print("</thead>");
            for (int i = 0; i< size; i++){
                out.print("<tr>");
                    out.print("<td>" +skills.get(i).getName()+"</td>");
                    out.print("<td style='text-align: center;'>" +(int)skills.get(i).getCount()+"</td>");
                    out.print("<td style='text-align: center;'>" +(int)skills.get(i).getAvgYear()+"</td>");
                out.print("</tr>");
            }
            out.print("</table>");

        %>
    </div>

    <div class="statistic-info">
        <h2>Top-3 industries</h2>
        <%
            List<Industry> industries = statistic.getTopIndustries();
            size = industries.size();
            if(size >= 3){
                size = 3;
            }
            out.print("<table cellpadding='5px;' style='padding-left: 30%;'>");
            out.print("<thead>");
            out.print("<th>Industry</th>");
            out.print("<th>Popularity</th>");
            out.print("</thead>");
            for (int i = 0; i< size; i++){
                out.print("<tr>");
                out.print("<td>" +industries.get(i).getName()+"</td>");
                out.print("<td style='text-align: center;'>" +(int)industries.get(i).getCount()+"</td>");
                out.print("</tr>");
            }
            out.print("</table>");
        %>
    </div>

    <div class="statistic-info">
        <h2>City of top industry</h2>
        <div style="float: left; position:relative; padding-left: 30%;">
            <table>
            <%
                List<String> cities = statistic.getCitiesForTopIndustry();
                for(String city: cities){
                    out.print("<tr>");
                    out.print("<td>" +city+"</td>");
                    out.print("</tr>");
                }
            %>
                </table>
        </div>
    </div>

    <div class="statistic-info">
        <h2>Country, where FindWith most popular:</h2>
        <table style="padding-left: 30%;">
            <%
                TopCountry country = statistic.getTopCountry();
                    out.print("<tr>");
                    out.print("<td>" +country.getCountry()+"</td></tr>");
                    out.print("<tr><td>Users quantity:" +(int)country.getCount()+"</td>");
                    out.print("</tr>");

            %>
        </table>
    </div>
</section>


<footer>
</footer>

</body>
</html>

