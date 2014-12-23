<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.grubmenow.service.datamodel.ProviderDAO"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemOfferDAO"%>
<%@page import="com.grubmenow.service.persist.PersistenceFactory"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemState"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemDAO"%>
<%@page import="java.util.List"%>
<html>
 <head> 
 <jsp:include page="common.jsp" />
 </head>
 <body>
 <div class="jqmWindow" id="popUpJqmWindow">
   Please wait...
 </div>
 
  <jsp:include page="header.jsp" >
  	<jsp:param name="dashboardTitle" value="Food Item Offer" />
  </jsp:include>
  
  <%
   List<FoodItemOfferDAO> foodItemOfferDAOs = PersistenceFactory.getInstance().getAllFoodItemOffer();
  %> 
  
  <a class="open_popup" href="add-edit-fooditemoffer-form">Add New</a> <br/>
   
  <table style="width: 100%;">
   <thead>
     <tr>
          <td class="table_header"> Id </td>
      	  <td class="table_header"> Food Item Id </td>
      	  <td class="table_header"> Provider Id </td>
      	  <td class="table_header"> Offer Description </td>
      	  <td class="table_header"> Offer Description Tags</td>
      	  <td class="table_header"> Offer Unit Price </td>
      	  <td class="table_header"> Currency </td>
      	  <td class="table_header"> Offer Quantity </td>
      	  <td class="table_header"> Available Quantity </td>
      	  <td class="table_header"> Offer Day </td>
      	  <td class="table_header"> Meal Type </td>
      	  <td class="table_header"> Is Food Delivery Option Available  </td>
      	  <td class="table_header"> Is Food Pick up Option Available  </td>
      	  <td class="table_header"> - </td>
      	  
     </tr>
   </thead>  
   <%
     Map<String, FoodItemDAO> foodLookUpMap = new HashMap<String, FoodItemDAO>();
     Map<String, ProviderDAO> providerMap = new HashMap<String, ProviderDAO>();
   %>	
   
   <% for(FoodItemOfferDAO foodItemOffer: foodItemOfferDAOs) { %>
   <%
   	FoodItemDAO foodItemDAO = null;
    ProviderDAO providerDAO = null;
    
    if(foodLookUpMap.containsKey(foodItemOffer.getFoodItemId())) {
    	foodItemDAO = foodLookUpMap.get(foodItemOffer.getFoodItemId());	
    } else {
    	foodItemDAO = PersistenceFactory.getInstance().getFoodItemById(foodItemOffer.getFoodItemId());
    	foodLookUpMap.put(foodItemOffer.getFoodItemId(), foodItemDAO);
    }
    
    if(providerMap.containsKey(foodItemOffer.getProviderId())) {
    	providerDAO = providerMap.get(foodItemOffer.getProviderId());	
    } else {
    	providerDAO = PersistenceFactory.getInstance().getProviderById(foodItemOffer.getProviderId());
    	providerMap.put(foodItemOffer.getProviderId(), providerDAO);
    }
    
   %>
   
   	<tr> 
   		  <td class="table_cell"> <%= foodItemOffer.getFoodItemOfferId() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getFoodItemId() %>  / <%=foodItemDAO.getFoodItemName() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getProviderId() %>  / <%=providerDAO.getProviderName() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferDescription() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferDescriptionTags() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferUnitPrice() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferCurrency() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferQuantity() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getAvailableQuantity() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferDay().toString() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getOfferMealType() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getIsFoodDeliveryOptionAvailable() %> </td>
      	  <td class="table_cell"> <%= foodItemOffer.getIsFoodPickUpOptionAvailable() %> </td>
      	  <td class="table_cell"> <a class="open_popup" href="add-edit-fooditemoffer-form?foodItemOfferId=<%= foodItemOffer.getFoodItemOfferId() %>">edit</a> </td>
   <% } %>
   </table>    
 </body>
</html>