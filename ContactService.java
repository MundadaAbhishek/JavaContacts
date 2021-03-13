package training.assg10;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ContactService {

	void addContact(Contact contact, List<Contact> contacts) {
		contacts.add(contact);
	}
	
	void removeContact(Contact contact, List<Contact> contacts) throws ContactNotFoundException{
		for(Contact i : contacts) {
			if(i.getContactName()==contact.getContactName()) {
				contacts.remove(i);
				break;
			}
		}
	}
	
	Contact searchContactByName(String Name, List<Contact> contacts) throws ContactNotFoundException{
		for(Contact i : contacts) {
			if(i.getContactName()==Name) {
				return i;
			}
		}
		return null;
	}
	
	List<Contact> searchContactByNumber(String number, List<Contact> contacts) throws ContactNotFoundException{
		List<Contact> toReturn = new ArrayList<>();
		for(Contact i : contacts) {
			List<String> numbers = i.getContactNumber();
			for(String s : numbers) {
				if(s.contains(number)) {
					toReturn.add(i);
				}
			}
		}
		return toReturn;
	}
	
	void addContactNumber(int contactId, String contactNo, List<Contact> contacts) {
		for(Contact i : contacts) {
			if(i.getContactId()==contactId) {
				i.getContactNumber().add(contactNo);
			}
		}
	}
	
	void sortContactsByName(List<Contact> contacts) {
		Collections.sort(contacts);
	}
	
	void readContactsFromFile(List<Contact> contacts, String fileName) {
		File file = new File(fileName);
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNext()) {
				Contact c = new Contact();
				String data[] = sc.nextLine().split(",");
				c.setContactId(Integer.parseInt(data[0]));
				c.setContactName(data[1]);
				c.setEmail(data[2]);
				c.setContactNumber(List.of(data[3].split("-")));
				
				contacts.add(c);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void serializeContactDetails(List<Contact> contacts, String fileName) {
		File file = new File(fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(contacts);
			oos.close();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	List<Contact> deserializeContact(String fileName){
		List<Contact> result = new ArrayList<>();
		File file = new File(fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			result = (List<Contact>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	Set<Contact> populateContactFromDb(){
		Set<Contact> dbContacts = new HashSet<>();
		Connection conn = Connect.establishConnection();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("select * from contact_tbl");
			
			Contact contact;
			while(results.next()) {
				contact = new Contact();
				contact.setContactId(results.getInt(1));
				contact.setContactName(results.getString(2));
				contact.setEmail(results.getString(3));
				if(results.getString(4) != null) {
					contact.setContactNumber(List.of(results.getString(4).split(",")));
				}
				dbContacts.add(contact);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return dbContacts;
	}
	
	Boolean addContacts(List<Contact> existingContact,Set<Contact> newContacts) {
		for(Contact i : newContacts) {
			existingContact.add(i);
		}
		return true;
	}
}
