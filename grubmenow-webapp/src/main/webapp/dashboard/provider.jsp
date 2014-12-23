<%@page import="com.grubmenow.service.persist.PersistenceFactory"%>
<%@page import="com.grubmenow.service.datamodel.ProviderState"%>
<%@page import="com.grubmenow.service.datamodel.ProviderDAO"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemDAO"%>
<%@page import="java.util.List"%>
<html>
 <head> 
 <jsp:include page="common.jsp" />
 
 <script>
	$(document).ready(function() {
   		$('#add').click(function(e) {
   			$('#add-form-container').show();
   			return true;	
 	     });
   		
   		$('#add-form-submit-button').click(function(e) {
 			 var link = $('#add-form').serialize()
   			 link = 'provider-add?' + link;
 			 
 			
 			 $('#add-form-submit-button').hide();
     		 $('#add-form-message-window').html('<p align="left">Saving .. &nbsp <img src="img/loader.gif"></p>');
      		 $('#add-form-message-window').load(link); 
			
   			return false;	
 	     });
   	});
 </script>
 </head>
 <body>
  <div class="jqmWindow" id="popUpJqmWindow">
   Please wait...
  </div>
 
  <jsp:include page="header.jsp" >
  	<jsp:param name="dashboardTitle" value="Provider" />
  </jsp:include>
  
  <%
   List<ProviderDAO> providerDAOs = PersistenceFactory.getInstance().getAllProvider();
  %> 
   
   <a class="open_popup" href="add-edit-provider-form">Add New</a> <br/>

    	
  <table style="width: 100%;">
   <thead>
     <tr>
          <td class="table_header"> Id </td>
      	  <td class="table_header"> Name </td>
      	  <td class="table_header"> Email </td>
      	  <td class="table_header"> Phone </td>
      	  <td class="table_header"> Address Street Number </td>
      	  <td class="table_header"> Address Street </td>
      	  <td class="table_header"> Address Apartment Number </td>
      	  <td class="table_header"> Address Zip Code </td>
      	  <td class="table_header">Address State</td>
      	  <td class="table_header">Address City</td>
      	  <td class="table_header">Image URL</td>
      	  <td class="table_header">is Online Payment Accepted</td>
      	  <td class="table_header">is Cash On Deliver Payment Accepted</td>
      	  <td class="table_header">is Card On Deliver Payment Accepted</td>
      	  <td class="table_header"> - </td>
     </tr>
   </thead>  	
   <% for(ProviderDAO providerDAO: providerDAOs) { %>
   	<tr>
   		  <td class="table_cell"> <%= providerDAO.getProviderId() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getProviderName() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getProviderEmailId() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getProviderPhoneNumber() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getProviderAddressStreetNumber() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getProviderAddressStreet() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getProviderAddressApartmentNumber() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getProviderAddressZipCode() %> </td>
   		  <td class="table_cell"> <%= providerDAO.getProviderAddressState() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getProviderAddressCity() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getProviderImageURL() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getIsOnlinePaymentAccepted() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getIsCashOnDeliverPaymentAccepted() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getIsCardOnDeliverPaymentAccepted() %> </td>
      	  <td class="table_cell"> <a class="open_popup" href="add-edit-provider-form?providerId=<%= providerDAO.getProviderId() %>">edit</a> </td>
   <% } %>
   </table>    
 </body>
</html>