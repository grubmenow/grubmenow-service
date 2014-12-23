<%@page import="com.grubmenow.service.datamodel.ProviderState"%>
<%@page import="com.grubmenow.service.datamodel.ProviderDAO"%>
<%@page import="com.grubmenow.service.persist.PersistenceFactory"%>
<%

 String providerId = "";
 String providerName = "";
 String providerEmailId = "";
 String providerAddressStreetNumber = "";
 String providerAddressStreet = "";
 String providerAddressApartmentNumber = "";
 String providerAddressZipCode = "";
 String providerAddressCity = "";
 String providerAddressState = "";
 String providerPhoneNumber = "";
 boolean isOnlinePaymentAccepted = true;
 boolean isCashOnDeliverPaymentAccepted = true;
 boolean isCardOnDeliverPaymentAccepted = false;
	
 if(request.getParameter("providerId") != null) {
	 ProviderDAO providerDAO = PersistenceFactory.getInstance().getProviderById(request.getParameter("providerId"));
	 providerId = providerDAO.getProviderId();
	 providerName = providerDAO.getProviderName();
	 providerEmailId = providerDAO.getProviderEmailId();
	 providerAddressStreetNumber = providerDAO.getProviderAddressStreetNumber();
	 providerAddressStreet = providerDAO.getProviderAddressStreet();
	 providerAddressApartmentNumber = providerDAO.getProviderAddressApartmentNumber();
	 providerAddressZipCode = providerDAO.getProviderAddressZipCode();
	 providerAddressCity = providerDAO.getProviderAddressCity();
	 providerAddressState = providerDAO.getProviderAddressState();
	 providerPhoneNumber = providerDAO.getProviderPhoneNumber();
	 isOnlinePaymentAccepted = providerDAO.getIsOnlinePaymentAccepted();
	 isOnlinePaymentAccepted = providerDAO.getIsCashOnDeliverPaymentAccepted();
	 isCardOnDeliverPaymentAccepted = providerDAO.getIsCardOnDeliverPaymentAccepted();
 }
%>
 
<script>
	$(document).ready(function() {
   		$('#add-form-submit-button').click(function(e) {
			 var link = $('#add-edit-form').serialize()
  			 link = 'add-edit-provider-action?' + link;
			 
			
			 $('#add-form-submit-button').hide();
    		 $('#add-form-message-window').html('<p align="left">Saving .. &nbsp <img src="img/loader.gif"></p>');
     		 $('#add-form-message-window').load(link); 
			
  			return false;	
	     });
   	});
</script>



<form id="add-edit-form">
  <table>
  	<tr>
  	  <td class="input_form_text"> ID </td>
  	  <td> <input class="input_form_text" type="text" name="providerId" value="<%=providerId %>"  hidden="true"/>  </td>
  	  <td class="form_info"> Provider Id </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Name </td>
  	  <td> <input class="input_form_text" type="text" name="providerName" value="<%=providerName %>"/> </td>
  	  <td class="form_info"> Example: Kapila's Kitchen </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Email Id </td>
  	  <td> <input class="input_form_text"  type="text" name="providerEmailId" value="<%=providerEmailId %>"/> </td>
  	  <td class="form_info"> Example: kapila.jain86@gmail.com </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Address StreetNumber </td>
  	  <td> <input class="input_form_text"  type="text" name="providerAddressStreetNumber" value="<%=providerAddressStreetNumber %>"/> </td>
  	  <td class="form_info"> Example: 4631 </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Address Street </td>
  	  <td> <input class="input_form_text"  type="text" name="providerAddressStreet" value="<%=providerAddressStreet %>"/> </td>
  	  <td class="form_info"> Example: 148th AVE NE </td> 
  	</tr>
  	  	<tr>
  	  <td class="input_form_text"> Address Apartment Number </td>
  	  <td> <input class="input_form_text"  type="text" name="providerAddressApartmentNumber" value="<%=providerAddressApartmentNumber %>"/> </td>
  	  <td class="form_info"> Example: QQ 202 </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Address ZipCode </td>
  	  <td> <input class="input_form_text"  type="text" name="providerAddressZipCode" value="<%=providerAddressZipCode %>"/> </td>
  	  <td class="form_info"> Example: 98007 </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Address City </td>
  	  <td> <input class="input_form_text"  type="text" name="providerAddressCity" value="<%=providerAddressCity %>"/> </td>
  	  <td class="form_info"> Example: Bellevue </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Address State </td>
  	  <td> <input class="input_form_text"  type="text" name="providerAddressState" value="<%=providerAddressState %>"/> </td>
  	  <td class="form_info"> Example: WA </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Phone Number </td>
  	  <td> <input class="input_form_text"  type="text" name="providerPhoneNumber" value="<%=providerPhoneNumber %>"/> </td>
  	  <td class="form_info"> Example: 2069159046 </td> 
  	</tr>
    <tr>
  	  <td class="input_form_text"> Online Payment Accepted </td>
  	  <td> 
  	      <select class="input_form_text"  name="isOnlinePaymentAccepted">
  			<option value="true" <%=isOnlinePaymentAccepted?"selected":"" %>>ACCEPTED</option>
  			<option value="false" <%=!isOnlinePaymentAccepted?"selected":"" %>>NOT-ACCEPTED</option>
  		  </select> 
  	   </td>
    </tr>
      	
      	      	
    <tr>
   	  <td class="input_form_text"> Cash On Delivery Accepted </td>
   	  <td> 
   	      <select class="input_form_text"  name="isCashOnDeliverPaymentAccepted">
   			<option value="true" <%=isCashOnDeliverPaymentAccepted?"selected":"" %>>ACCEPTED</option>
   			<option value="false" <%=!isCashOnDeliverPaymentAccepted?"selected":"" %>>NOT-ACCEPTED</option>
   		  </select> 
   	   </td>
    </tr>
      	
    <tr>
   	  <td class="input_form_text"> Card On Delivery Accepted </td>
   	  <td> 
   	      <select class="input_form_text"  name="isCardOnDeliverPaymentAccepted">
   			<option value="true" <%=isCardOnDeliverPaymentAccepted?"selected":"" %>>ACCEPTED</option>
   			<option value="false" <%=!isCardOnDeliverPaymentAccepted?"selected":"" %>>NOT-ACCEPTED</option>
   		  </select> 
   	   </td>
    </tr>

  	<tr>
  	  <td class="input_form_text"> Code </td>
  	  <td> <input class="input_form_text"  type="text" name="code" /> </td>
  	  <td class="form_info"> Access Code </td> 
  	</tr>

  	<tr>
  	  <td></td>
  	  <td> <input id="add-form-submit-button" type="submit" value="Add">
  	       <div id="add-form-message-window"> </div> </td> 
  	</tr>
  </table>
 
</form>