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
   			 link = 'dashboard/fooditem-add?' + link;
 			 
 			
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
   List<FoodItemDAO> foodItemDAOs = (List<FoodItemDAO>) request.getAttribute("foodItems");
  %> 
   
   <a id="add" href="#">Add New</a> <br/>
   <div id="add-form-container" style="display: none;">
    <form id="add-form">
      <table>
      	<tr>
      	  <td class="input_form_text"> Name </td>
      	  <td> <input class="input_form_text" type="text" name="foodItemName" /> </td> 
      	</tr>
      	<tr>
      	  <td class="input_form_text"> Image Url </td>
      	  <td> <input class="input_form_text"  type="text" name="foodItemImageUrl" /> </td> 
      	</tr>
      	<tr>
      	  <td class="input_form_text"> Description </td>
      	  <td> <textarea class="input_form_text"  name="foodItemDescription" ></textarea> </td> 
      	</tr>
      	<tr>
      	  <td class="input_form_text">Tags</td>
      	  <td> <textarea class="input_form_text"  name="foodItemDescriptionTags" ></textarea> </td> 
      	</tr>
      	<tr>
      	  <td class="input_form_text"> State </td>
      	  <td> 
      	      <select class="input_form_text"  name="foodItemState" multiple="multiple">
      			<% for(FoodItemState state: FoodItemState.values()) { %>
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
      	  <td class="table_header"> Image Url </td>
      	  <td class="table_header"> Description </td>
      	  <td class="table_header"> Tags </td>
      	  <td class="table_header"> State </td>
     </tr>
   </thead>  	
   <% for(FoodItemDAO foodItem: foodItemDAOs) { %>
   	<tr>
   		  <td class="table_cell"> <%= foodItem.getFoodItemId() %> </td>
      	  <td class="table_cell"> <%= foodItem.getFoodItemName() %> </td>
      	  <td class="table_cell"> <%= foodItem.getFoodItemImageUrl() %> </td>
      	  <td class="table_cell"> <%= foodItem.getFoodItemDescription() %> </td>
      	  <td class="table_cell"> <%= foodItem.getFoodItemDescriptionTags() %> </td>
      	  <td class="table_cell"> <%= foodItem.getFoodItemState() %> </td>
   <% } %>
   </table>    
 </body>
</html>