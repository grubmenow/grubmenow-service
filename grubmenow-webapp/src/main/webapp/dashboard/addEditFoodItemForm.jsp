<%@page import="com.grubmenow.service.datamodel.FoodItemState"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemDAO"%>
<%@page import="com.grubmenow.service.persist.PersistenceFactory"%>
<%@page import="com.grubmenow.service.model.FoodItem"%>
<%

 String foodItemId = "";
 String foodItemName = "";
 String foodItemImageUrl = "";

 if(request.getParameter("foodItemId") != null) {
	 FoodItemDAO foodItem = PersistenceFactory.getInstance().getFoodItemById(request.getParameter("foodItemId"));
	 foodItemId = foodItem.getFoodItemId();
	 foodItemName = foodItem.getFoodItemName();
	 foodItemImageUrl = foodItem.getFoodItemImageUrl();
 }
%>
 
<script>
	$(document).ready(function() {
   		$('#add-form-submit-button').click(function(e) {
			 var link = $('#add-edit-form').serialize()
  			 link = 'add-edit-fooditem-action?' + link;
			 
			
			 $('#add-form-submit-button').hide();
    		 $('#add-form-message-window').html('<p align="left">Saving .. &nbsp <img src="img/loader.gif"></p>');
     		 $('#add-form-message-window').load(link); 
			
  			return false;	
	     });
   	});
</script>



<form id="add-edit-form">
  <input class="input_form_text" type="text" name="foodItemId" value="<%=foodItemId %>" hidden="true"/>
  <table>
  	<tr>
  	  <td class="input_form_text"> Name </td>
  	  <td> <input class="input_form_text" type="text" name="foodItemName" value="<%=foodItemName %>"/> </td>
  	  <td class="form_info"> Name of the food item </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Image Url </td>
  	  <td> <input class="input_form_text"  type="text" name="foodItemImageUrl" value="<%=foodItemImageUrl %>"/> </td>
  	  <td class="form_info"> Url of the Food Item. Example: http://grubmenow.com/img/curd_rice.jpg </td> 
  	</tr>
  	<tr>
  	  <td></td>
  	  <td> <input id="add-form-submit-button" type="submit" value="Add">
  	       <div id="add-form-message-window"> </div> </td> 
  	</tr>
  </table>
 
</form>