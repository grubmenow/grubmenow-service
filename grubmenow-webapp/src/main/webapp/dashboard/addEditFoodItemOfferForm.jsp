<%@page import="com.grubmenow.service.datamodel.ProviderDAO"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemOfferDAO"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemState"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemDAO"%>
<%@page import="com.grubmenow.service.persist.PersistenceFactory"%>
<%@page import="com.grubmenow.service.model.FoodItem"%>
<%

 String foodItemOfferId = "";
 String foodItemId = "";
 String providerId = "";
 String offerDescription = "";
 String offerUnitPrice = "";
 String offerQuantity = "";
 String offerDescriptionTags = "";
 String offerDayDD = "";
 String offerDayMM = "";
 String offerDayYYYY = "";
 
 if(request.getParameter("foodItemOfferId") != null) {
	 FoodItemOfferDAO foodItemOffer = PersistenceFactory.getInstance().getFoodItemOfferById(request.getParameter("foodItemOfferId"));
	 foodItemOfferId = foodItemOffer.getFoodItemId();
	 foodItemId = foodItemOffer.getFoodItemId();
	 providerId = foodItemOffer.getProviderId();
	 offerDescription = foodItemOffer.getOfferDescription();
	 offerDescriptionTags = foodItemOffer.getOfferDescriptionTags();
	 offerUnitPrice = Integer.toString(foodItemOffer.getOfferUnitPrice());
	 offerQuantity = Integer.toString(foodItemOffer.getOfferQuantity());
	 
	 offerDayDD = Integer.toString(foodItemOffer.getOfferDay().getDayOfMonth());
	 offerDayMM = Integer.toString(foodItemOffer.getOfferDay().getMonthOfYear());
	 offerDayYYYY = Integer.toString(foodItemOffer.getOfferDay().getYear());
 }
%>
 
<script>
	$(document).ready(function() {
   		$('#add-form-submit-button').click(function(e) {
			 var link = $('#add-edit-form').serialize()
  			 link = 'add-edit-fooditemoffer-action?' + link;
			 
			
			 $('#add-form-submit-button').hide();
    		 $('#add-form-message-window').html('<p align="left">Saving .. &nbsp <img src="img/loader.gif"></p>');
     		 $('#add-form-message-window').load(link); 
			
  			return false;	
	     });
   	});
</script>



<form id="add-edit-form">
  <input class="input_form_text" type="text" name="foodItemOfferId" value="<%=foodItemOfferId %>" hidden="true"/>
  <table>
  	<tr>
  	  <td class="input_form_text"> Name </td>
  	  <td>
  	    	<select class="input_form_text"  name="foodItemId">
  	    	 <%for(FoodItemDAO foodItemDAO : PersistenceFactory.getInstance().getAllFoodItem()) { %>
  	    	 	<option value="<%=foodItemDAO.getFoodItemId() %>" <%=StringUtils.equals(foodItemDAO.getFoodItemId(), foodItemId)?"selected":"" %>> <%=foodItemDAO.getFoodItemName() %></option>
  	    	 <%}%>
  	    	</select> 
  	  </td>
  	  <td class="form_info"> Food Item </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Provider </td>
  	  <td>
  	    	<select class="input_form_text"  name="providerId">
  	    	 <%for(ProviderDAO providerDAO: PersistenceFactory.getInstance().getAllProvider()) { %>
  	    	 	<option value="<%=providerDAO.getProviderId() %>" <%=StringUtils.equals(providerDAO.getProviderId(), providerId)?"selected":"" %>> <%=providerDAO.getProviderName() %></option>
  	    	 <%}%>
  	    	</select> 
  	  </td>
  	  <td class="form_info"> Provider </td> 
  	</tr>

  	<tr>
  	  <td class="input_form_text"> Offer Day </td>
  	  <td> <br/>DD: <input class="input_form_text"  type="text" name="offerDayDD" value="<%=offerDayDD %>"/>
  	  	   <br/>MM: <input class="input_form_text"  type="text" name="offerDayMM" value="<%=offerDayMM %>"/>
  	       <br/>YYYY: <input class="input_form_text"  type="text" name="offerDayYYYY" value="<%=offerDayYYYY %>"/> 
  	   </td>
  	  <td class="form_info"> 01  11  2014 (for 1st Nov 2014) </td> 
  	</tr>
  	
  	<tr>
  	  <td class="input_form_text"> Offer Description </td>
  	  <td> <input class="input_form_text"  type="text" name="offerDescription" value="<%=offerDescription %>"/> </td>
  	  <td class="form_info"> Example: Steamed rice preparation, served with sambar. (5 pieces) </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Offer Tags </td>
  	  <td> <input class="input_form_text"  type="text" name="offerDescriptionTags" value="<%=offerDescriptionTags %>"/> </td>
  	  <td class="form_info"> Comma separated Tag. Example: Spicy, Vegetarian </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Offer Quantity </td>
  	  <td> <input class="input_form_text"  type="text" name="offerQuantity" value="<%=offerQuantity %>"/> </td>
  	  <td class="form_info"> Example: 10 </td> 
  	</tr>
  	
  	<tr>
  	  <td class="input_form_text"> Offer Unit Price (in cents) </td>
  	  <td> <input class="input_form_text"  type="text" name="offerUnitPrice" value="<%=offerUnitPrice %>"/> </td>
  	  <td class="form_info"> Example: 699 (for $6.99) </td> 
  	</tr>
  	
  	<tr>
  	  <td></td>
  	  <td> <input id="add-form-submit-button" type="submit" value="Add">
  	       <div id="add-form-message-window"> </div> </td> 
  	</tr>
  </table>
 
</form>