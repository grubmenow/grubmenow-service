<%@page import="com.grubmenow.service.datamodel.FoodItemOfferDAO"%>
<%@page import="com.grubmenow.service.persist.PersistenceFactory"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemState"%>
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
   			 link = 'fooditem-add?' + link;
 			 
 			
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
   List<FoodItemOfferDAO> foodItemOfferDAOs = PersistenceFactory.getInstance().getAllFoodItemOffer();
  %> 
   
  <table style="width: 100%;">
   <thead>
     <tr>
          <td class="table_header"> Id </td>
      	  <td class="table_header"> Food Item Id </td>
      	  <td class="table_header"> Provider Id </td>
      	  <td class="table_header"> Offer Description </td>
      	  <td class="table_header"> Offer Description Tags</td>
      	  <td class="table_header"> State </td>
      	  <td class="table_header"> Offer Unit Price </td>
      	  <td class="table_header"> Currency </td>
      	  <td class="table_header"> Quantity </td>
      	  <td class="table_header"> Offer Day </td>
      	  <td class="table_header"> Meal Type </td>
      	  <td class="table_header"> Is Food Delivery Option Available  </td>
      	  <td class="table_header"> Is Food Pick up Option Available  </td>
      	  
     </tr>
   </thead>  	
   <% for(FoodItemOfferDAO foodItemOffer: foodItemOfferDAOs) { %>
   	<tr>
   		  <td class="table_cell"> <%= foodItemOffer.getFoodItemOfferId() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getFoodItemId() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getProviderId() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferDescription() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferDescriptionTags() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferUnitPrice() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferCurrency() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferQuantity() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferDay() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferMealType() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getIsFoodDeliveryOptionAvailable() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getIsFoodPickUpOptionAvailable() %> </td>
   <% } %>
   </table>    
 </body>
</html>