<%@page import="com.grubmenow.service.datamodel.ProviderState"%>
<%@page import="com.grubmenow.service.datamodel.ProviderDAO"%>
<%@page import="com.grubmenow.service.FoodItem"%>
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
   			 link = '/provider-add?' + link;
 			 
 			
 			 $('#add-form-submit-button').hide();
     		 $('#add-form-message-window').html('<p align="left">Saving .. &nbsp <img src="/img/loader.gif"></p>');
      		 $('#add-form-message-window').load(link); 
			
   			return false;	
 	     });
   	});
 </script>
 </head>
 <body>
  <jsp:include page="header.jsp" >
  	<jsp:param name="dashboardTitle" value="Food Item" />
  </jsp:include>
  
  <%
   List<ProviderDAO> providerDAOs = (List<ProviderDAO>) request.getAttribute("providers");
  %> 
   
   <a id="add" href="#">Add New</a> <br/>
   <div id="add-form-container" style="display: none;">
    <form id="add-form">
      <table>
      	<tr>
      	  <td class="input_form_text"> Name </td>
      	  <td> <input class="input_form_text" type="text" name="providerName" /> </td> 
      	</tr>
      	<tr>
      	  <td class="input_form_text"> Street Number </td>
      	  <td> <input class="input_form_text"  type="text" name="providerAddressStreetNumber" /> </td> 
      	</tr>
      	<tr>
      	  <td class="input_form_text"> Street </td>
      	  <td> <textarea class="input_form_text"  name="providerAddressStreet" ></textarea> </td> 
      	</tr>
      	<tr>
      	  <td class="input_form_text">Apartment Number</td>
      	  <td> <textarea class="input_form_text"  name="providerAddressApartmentNumber" ></textarea> </td> 
      	</tr>
      	<tr>
      	  <td class="input_form_text">State</td>
      	  <td> <textarea class="input_form_text"  name="providerAddressState" ></textarea> </td> 
      	</tr>
      	<tr>
      	  <td class="input_form_text">City</td>
      	  <td> <textarea class="input_form_text"  name="providerAddressCity" ></textarea> </td> 
      	</tr>
      	<tr>
      	  <td class="input_form_text">Image Url</td>
      	  <td> <textarea class="input_form_text"  name="providerImageURL" ></textarea> </td> 
      	</tr>
      	
      	<tr>
      	  <td class="input_form_text"> Online Payment Accepted </td>
      	  <td> 
      	      <select class="input_form_text"  name="isOnlinePaymentAccepted">
      			<option value="true">ACCEPTED</option>
      			<option value="false">NOT-ACCEPTED</option>
      		  </select> 
      	   </td>
      	</tr>
      	
      	      	
      	<tr>
      	  <td class="input_form_text"> Cash On Delivery Accepted </td>
      	  <td> 
      	      <select class="input_form_text"  name="isCashOnDeliverPaymentAccepted">
      			<option value="true">ACCEPTED</option>
      			<option value="false">NOT-ACCEPTED</option>
      		  </select> 
      	   </td>
      	</tr>
      	
      	<tr>
      	  <td class="input_form_text"> Card On Delivery Accepted </td>
      	  <td> 
      	      <select class="input_form_text"  name="isCardOnDeliverPaymentAccepted">
      			<option value="true">ACCEPTED</option>
      			<option value="false">NOT-ACCEPTED</option>
      		  </select> 
      	   </td>
      	</tr>


      	<tr>
      	  <td class="input_form_text"> State </td>
      	  <td> 
      	      <select class="input_form_text"  name="providerState" multiple="multiple">
      			<% for(ProviderState state: ProviderState.values()) { %>
      				<option value="<%=state.name()%>"><%=state.name()%></option>
      			<%} %>
      		  </select> 
      	   </td> 
      	</tr>
      	<tr>
      	  <td></td>
      	  <td> <input id="add-form-submit-button" type="submit" value="Add">
      	       <div id="add-form-message-window"> </div> </td> 
      	</tr>
      </table>
	    
    </form>
   </div> <br/>
    	
    	
  <table style="width: 100%;">
   <thead>
     <tr>
          <td class="table_header"> Id </td>
      	  <td class="table_header"> Name </td>
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
     </tr>
   </thead>  	
   <% for(ProviderDAO providerDAO: providerDAOs) { %>
   	<tr>
   		  <td class="table_cell"> <%= providerDAO.getProviderId() %> </td>
      	  <td class="table_cell"> <%= providerDAO.getProviderName() %> </td>
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
   <% } %>
   </table>    
 </body>
</html>