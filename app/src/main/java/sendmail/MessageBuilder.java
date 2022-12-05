package sendmail;

import java.util.LinkedList;

import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;

public class MessageBuilder {
	static class ItemBuilder {
		MessageBuilder builder;
		ItemBody item = new ItemBody();
		ItemBuilder() {
			item = new ItemBody();
		}
		ItemBuilder(MessageBuilder builder) {
			this.builder = builder;
			item = new ItemBody();
		}
		ItemBody build() {
			return item;
		}
		
		ItemBuilder contentType(BodyType type) {
			item.contentType = type;
			return this;
		}
		ItemBuilder content(String content) {
			item.content = content;
			return this;
		}
		MessageBuilder endItem() {
			return builder;
		}

	}
	
	MessageBuilder item(ItemBody item) {
		message.body = item;
		return this;
	}
	
	ItemBuilder item() {
		ItemBuilder item = new ItemBuilder(this);
		message.body = item.build();
		return item;
	}
	static Message message = new Message();
	LinkedList<Recipient> toRecipientsList = new LinkedList<Recipient>();
	LinkedList<Recipient> toCcRecipientsList = new LinkedList<Recipient>();
	LinkedList<Recipient> toBccRecipientsList = new LinkedList<Recipient>();

	
	
	Message build() {
    	message.toRecipients = toRecipientsList;
    	if (toCcRecipientsList.size() > 0) {
    		message.ccRecipients = toCcRecipientsList;
    	}
		return message;
	}
	
	MessageBuilder subject(String subject) {
		message.subject = subject;
		return this;
	}
	
	enum RecipientType {
		CC,   // Carbon copy
		BCC,  // Blind csrbon copy
		TO    // Recipient
	}
	
	MessageBuilder addRecipient(String email, RecipientType type) {
    	Recipient toRecipients = new Recipient();
    	EmailAddress emailAddress = new EmailAddress();
    	emailAddress.address = email;
    	toRecipients.emailAddress = emailAddress;
    	switch (type) {
    		case CC:
    			toCcRecipientsList.add(toRecipients);
    			break;
    		case TO: 
    			toRecipientsList.add(toRecipients);
    			break;
    		case BCC:
    			toBccRecipientsList.add(toRecipients);
    			break;
    	}
    	return this;
	}
	
	MessageBuilder addRecipient(String email) {
		return addRecipient(email,  RecipientType.TO);
	}
	
	MessageBuilder addCc(String email) {
      	return addRecipient(email, RecipientType.CC);
	}
	
	MessageBuilder addBcc(String email) {
      	return addRecipient(email, RecipientType.BCC);
	}
	
	MessageBuilder deliveryReceipt(Boolean receipt) {
		message.isDeliveryReceiptRequested = receipt;
		return this;
	}
	
	MessageBuilder isDraft(Boolean isDraft) {
		message.isDraft = isDraft;
		return this;
	}

}