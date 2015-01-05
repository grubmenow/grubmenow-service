<%@page import="com.grubmenow.service.datamodel.FoodItemState"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemDAO"%>
<%@page import="com.grubmenow.service.persist.PersistenceFactory"%>
<%@page import="com.grubmenow.service.model.FoodItem"%>
 
<script>
	$(document).ready(function() {
   		$('#add-form-submit-button').click(function(e) {
			 var request = {
					 foodItemName : $("#foodItemName").val(),
					 foodItemImageUrl : $("#foodItemImageUrl").val()
			 }
			 
			 $.ajax({
				type: "POST",
				dataType: "json",
	            contentType: "application/json",
				url: "../api/foodItem",
				data: JSON.stringify(request),
				success: function(res)
				{
					$('#apiOutput').val(JSON.stringify(res, undefined, 2));
					$('#add-form-message-window').html('<div align="left" style="font-weight: bold; color: green">Success!!. Press ESC to close</div>');
				},
				error:  function(res) {
                    $('#apiOutput').val(JSON.stringify(res, undefined, 2));
                    $('#add-form-submit-button').show();
                    $('#add-form-message-window').html('<div align="left" style="font-weight: bold; color: red;">Fault. Press ESC to close</div>');
                }
			 });
			
			 $('#add-form-submit-button').hide();
    		 $('#add-form-message-window').html('<p align="left">Saving .. &nbsp <img src="img/loader.gif"></p>');
			
  			return false;	
	     });
   	});
</script>



<form id="add-edit-form">
  <table>
  	<tr>
  	  <td class="input_form_text"> Name </td>
  	  <td> <input class="input_form_text" id="foodItemName" type="text" name="foodItemName" /> </td>
  	  <td class="form_info"> Name of the food item </td> 
  	</tr>
  	<tr>
  	  <td class="input_form_text"> Image Url </td>
  	  <td> <input class="input_form_text" id="foodItemImageUrl"  type="text" name="foodItemImageUrl" /> </td>
  	  <td class="form_info"> Url of the Food Item. Example: http://somewebsite.com/img/curd_rice.jpg </td> 
  	</tr>
  	<tr>
  	  <td></td>
  	  <td> <input id="add-form-submit-button" type="submit" value="Add">
  	       <div id="add-form-message-window"> </div> </td> 
  	</tr>
  </table>
  <div>
    <textarea id="apiOutput" style="width: 100%; height: 100%; resize: vertical;" rows=4></textarea>
  </div>
 
</form>