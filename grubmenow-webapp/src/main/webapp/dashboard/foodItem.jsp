<%@page import="com.grubmenow.service.persist.PersistenceFactory"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemState"%>
<%@page import="com.grubmenow.service.datamodel.FoodItemDAO"%>
<%@page import="java.util.List"%>
<html>
 <head> 
 <%@ include file="common.jsp" %>
 </head>
 <body>
 <div class="jqmWindow" id="popUpJqmWindow">
   Please wait...
 </div>
 
  <jsp:include page="header.jsp" >
  	<jsp:param name="dashboardTitle" value="Food Item" />
  </jsp:include>
  
  <%
   List<FoodItemDAO> foodItemDAOs = PersistenceFactory.getInstance().getAllFoodItem();
  %> 
   
   <a class="open_popup" href="add-new-fooditem-form">Add New <B>(Downloads the image and saves in our storage after resizing) </B></a> <br/>
   <a class="open_popup" href="add-edit-fooditem-form">Add New</a> <br/>
    	
  <table style="width: 100%;">
   <thead>
     <tr>
          <td class="table_header"> Id </td>
      	  <td class="table_header"> Name </td>
      	  <td class="table_header"> Image Url </td>
      	  <td class="table_header"> Description </td>
      	  <td class="table_header"> Tags </td>
      	  <td class="table_header"> State </td>
      	  <td class="table_header"> - </td>
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
      	  <td class="table_cell"> <a class="open_popup" href="add-edit-fooditem-form?foodItemId=<%= foodItem.getFoodItemId() %>">edit</a> </td>
   <% } %>
   </table>    
 </body>
</html>