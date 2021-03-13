package training.assg10;

import java.util.List;

public class Contact implements Comparable<Contact>{

	private int contactId;
	private String contactName;
	private String email;
	private List<String> contactNumber;
	
	Contact(){
		super();
	}
	Contact(int contactId, String contactName, String email, List<String> contactNumber){
		this.contactId = contactId;
		this.contactName = contactName;
		this.email = email;
		this.contactNumber = contactNumber;
	}
	
	void setContactId(int contactId) {
		this.contactId = contactId;
	}
	int getContactId() {
		return this.contactId;
	}
	void setContactName(String contactName) {
		this.contactName = contactName;
	}
	String getContactName() {
		return this.contactName;
	}
	void setEmail(String email) {
		this.email = email;
	}
	String getEmail() {
		return this.email;
	}
	void setContactNumber(List<String> contactNumber) {
		this.contactNumber = contactNumber;
	}
	List<String> getContactNumber() {
		return this.contactNumber;
	}
	@Override
	public int compareTo(Contact o) {
		return this.getContactName().compareTo(o.getContactName());
	}
}
