package findwith.DAORealizations;

import com.mongodb.*;
import findwith.DAO.UserDAO;
import findwith.Entities.Contact;
import findwith.Entities.InnerEntities.Course;
import findwith.Entities.InnerEntities.Location;
import findwith.Entities.InnerEntities.Project;
import findwith.Entities.InnerEntities.UserEducation;
import findwith.Entities.Person;
import findwith.controllers.MongoDBController;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by milinchuk on 11/24/14.
 */
public class UserDAOImpl implements UserDAO {
    DB dataBase;
    DBCollection users;

    public UserDAOImpl(String dbClient, String dbName, String collection) throws UnknownHostException {
        MongoClient client = MongoDBController.getMongoDBClient(dbClient);
        dataBase = MongoDBController.getMongoDataBase(client, dbName);
        users = MongoDBController.getMongoDBCollection(dataBase, collection);
    }
    private Person fillUser(DBObject u){
        Person newPerson = new Person();
        newPerson.setId(((ObjectId)u.get("_id")).toString());
        newPerson.setFirstname((String)u.get("firstname"));
        newPerson.setLastname((String)u.get("secondname"));

        newPerson.setBirthday((Date)u.get("birthday"));
        newPerson.setLogin((String) u.get("login"));
        newPerson.setPassword((String)u.get("password"));
        newPerson.setEmail((String) u.get("email"));
        newPerson.setIndustry((String)u.get("industry"));
        newPerson.setPhoto((String)u.get("photo"));
        newPerson.setPhone((String) u.get("phone"));

        Location location = new Location();
        DBObject object = (DBObject)u.get("location");
        location.setCountry(object.get("country").toString());
        location.setCity(object.get("city").toString());
        newPerson.setLocation(location);


        DBObject edu = (DBObject)(u.get("education"));
            UserEducation education = new UserEducation();
            education.setUniversity(edu.get("university").toString());
            education.setFaculty(edu.get("faculty").toString());
            education.setGraduateYear(edu.get("year").toString() == "" ? null : Integer.parseInt(edu.get("year").toString()));

        newPerson.setEducation(education);

        BasicDBList cursor = (BasicDBList)(u.get("projects"));
        if(cursor != null){
            List<Project> projects = new LinkedList<Project>();
            for(Object proj: cursor){
                Project p = new Project();
                p.setReference(((DBObject) proj).get("reference").toString());
                p.setDescription(((DBObject) proj).get("description").toString());
                projects.add(p);
            }
             newPerson.setProjects(projects);
        }
        List<String> skills = new LinkedList<String>();
        BasicDBList skill = (BasicDBList)u.get("skills");
        if(skill != null){
            for(Object s: skill){
                skills.add((String)s);
            }
        }
        newPerson.setSkills(skills);

        List<Course> courses = new LinkedList<Course>();
        BasicDBList crs = (BasicDBList)u.get("courses");
        if(crs != null){
            for(Object c: crs){
                DBObject obj = (DBObject)c;
                Course course = new Course();
                course.setProvider((String)obj.get("provider"));
                course.setName((String)obj.get("name"));
                course.setYear(Integer.parseInt((String) obj.get("year")));
                courses.add(course);
            }
        }
        newPerson.setCourses(courses);

        List<String> followings = new LinkedList<String>();
        BasicDBList follows = (BasicDBList)u.get("followings");
        if(follows != null){
            for(Object f: follows){
                followings.add((String) f);
            }
        }
        newPerson.setFollowings(followings);

        List<String> contacts = new LinkedList<String>();
        BasicDBList contcts = (BasicDBList)u.get("contacts");
        if(contcts != null){
            for(Object c: contcts){
                contacts.add((String)c);
            }
        }
        newPerson.setContacts(contacts);

        newPerson.setContactProfiles(getContacts(contacts));

        return newPerson;
    }
    @Override
    public Person getUser(String login) {
            BasicDBObject findQuery = new BasicDBObject("login", login);
            DBCursor usrs = users.find(findQuery);
            Person newPerson = new Person();
            for(DBObject u: usrs){
                newPerson = fillUser(u);
            }
            return newPerson;
    }

    public Person getUserByID(String id) {
        ObjectId _id = new ObjectId(id);
        BasicDBObject findQuery = new BasicDBObject("_id", _id);
        DBCursor usrs = users.find(findQuery);
        Person newPerson = new Person();
        for(DBObject u: usrs){
            newPerson = fillUser(u);
        }

        return newPerson;
    }

    public List<Contact> getContacts(List<String> contactIds){
        BasicDBList contacts = new BasicDBList();
        for(String id: contactIds){
            ObjectId _id = new ObjectId(id);
            BasicDBObject o = new BasicDBObject("_id", _id);
            contacts.add(o);
        }
        List<Contact> contactList = new LinkedList<Contact>();
        if(!contacts.isEmpty()){
            BasicDBObject query = new BasicDBObject("$or", contacts);
            DBCursor result = users.find(query);

            while(result.hasNext()){
                Contact c = new Contact();
                DBObject contact = result.next();
                c.setFirstname(contact.get("firstname").toString());
                c.setLastname(contact.get("secondname").toString());
                c.setEmail(contact.get("email").toString());
                c.setPhoto(contact.get("photo").toString());
                c.set_id((contact.get("_id")).toString());
                contactList.add(c);
            }
        }
        return contactList;
    }

    @Override
    public boolean addUser(Person person) {
        try {
            BasicDBObject location = new BasicDBObject("country", person.getLocation().getCountry()).append("city", person.getLocation().getCity());
            BasicDBObject education = new BasicDBObject("university", person.getEducation().getUniversity()).
                    append("faculty", person.getEducation().getFaculty()).
                    append("year", person.getEducation().getGraduateYear());

            BasicDBObject addNewUser = new BasicDBObject("login", person.getLogin()).
                    append("password", person.getPassword()).
                    append("firstname", person.getFirstname()).
                    append("secondname", person.getLastname()).
                    append("birthday", person.getBirthday()).
                    append("location", location).
                    append("education", education).
                    append ("email", person.getEmail()).
                    append("phone", person.getPhone()).
                    append("industry", person.getIndustry());

            users.save(addNewUser);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<Person> getAllUsers() {
        DBCursor usrs = users.find();
        List<Person> listPersons = new LinkedList<Person>();
        for(DBObject u: usrs){
            Person newPerson = new Person();
            newPerson = fillUser(u);
            listPersons.add(newPerson);
        }

        return listPersons;
    }

    @Override
    public boolean updateUser(String login, Person updatedPerson) {
        try{
            BasicDBObject findQuery = new BasicDBObject("login", login);
            users.remove(findQuery);
            addUser(updatedPerson);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteUser(String login) {
        try{
            BasicDBObject findQuery = new BasicDBObject("login", login);
            users.remove(findQuery);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean isExist(String login, String password) {
        BasicDBObject findQuery = new BasicDBObject("login", login).append("password", password);
        DBCursor usrs = users.find(findQuery);
        if(usrs.size() == 1){
            return true;
        }
        else
            return false;
    }
    public boolean isExist(String id) {
        ObjectId _id = new ObjectId(id);
        BasicDBObject findQuery = new BasicDBObject("_id", _id);
        DBCursor usrs = users.find(findQuery);
        if(usrs.size() == 1){
            return true;
        }
        else
            return false;
    }
}
