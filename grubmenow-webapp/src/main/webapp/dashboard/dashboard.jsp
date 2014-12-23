<html>
 <head> 
  <%@ include file="common.jsp" %>
  
   <script>
	$(document).ready(function() {
		
		$('#apiSelection').change(function(e) {
			var obj = new Object();
			var selectedAPI = $("#apiSelection option:selected").val();
			
			$('#apiEndpoint').text('/api/' + selectedAPI);				
			
			if(selectedAPI == 'searchFoodItems') {
				obj.zipCode = '98007';
				obj.availableDay = 'Today';
				obj.radius = 10;
			}
			
			if(selectedAPI == 'getDetailPageResults') {
				obj.foodItemId = '225636';
				obj.availableDay = 'Today';
			}
			
			if(selectedAPI == 'getProviderMenu') {
				obj.providerId = 'ProviderId1';
				obj.availableDay = 'Today';
			}
			
			if(selectedAPI == 'placeOrder') {
				obj.providerId = 'ProviderId1';
				obj.websiteAuthenticationToken = '10152843609422975';
				obj.onlinePaymentToken = '10152843609422975';
				obj.deliveryMethod = 'CUSTOMER_PICKUP';
				obj.paymentMethod = 'ONLINE_PAYMENT';

				var amount = new Object();
				amount.value = 1531;
				amount.currency = 'USD';
				obj.orderAmount = amount;
				
				var orderItem = new Object();
				orderItem.foodItemOfferId = 'mowqweoedi';
				orderItem.quantity = 2;
				obj.orderItems = [orderItem];
			}
			
			if(selectedAPI == 'submitOrderFeedback') {
				obj.websiteAuthenticationToken = '10152843609422975';
				obj.rating = 4;
				obj.orderId = '4a05f5f5-10da-4546-b0c2-06d332064795';
				obj.feedback = 'Nice .!';
			}
			
			if(selectedAPI == 'getCustomerOrder') {
				obj.websiteAuthenticationToken = '10152843609422975';
				obj.orderId = '4a05f5f5-10da-4546-b0c2-06d332064795';
			}
			
			if(selectedAPI == 'getCustomerOrderFeedback') {
				obj.websiteAuthenticationToken = '10152843609422975';
				obj.orderId = '4a05f5f5-10da-4546-b0c2-06d332064795';
			}

			$('#apiInput').val(JSON.stringify(obj, undefined, 2));
			$('#apiOutput').val('');
		});
		
		$('#apiSelection').trigger("change");
		
   		$('#apiTrigger').click(function(e) {
   		  
   		  $('#apiOutput').val('Processing..');
   		  var inputData = $('#apiInput').val();
		  var selectedAPI = 'api/' + $("#apiSelection option:selected").val();	
   		  var apiURL = '../' + selectedAPI;
		  
		  $.ajax({
   		    type: "POST",
   			dataType: "json",
   			contentType: "application/json",
   			url: apiURL,
   			data: inputData, 
   			success: function(res) {
   						$('#apiOutput').val(JSON.stringify(res, undefined, 2));
   					},
   			error:  function(res) {
						$('#apiOutput').val(JSON.stringify(res, undefined, 2));
					}
   		  });
        });
   	});
 </script>
</head>

<body>
    
  <jsp:include page="header.jsp" >
  	<jsp:param name="dashboardTitle" value="Home" />
  </jsp:include>
  
  <a href="fooditem"> Food Item </a>
  <br/>
  <a href="provider"> Provider </a>
  <br/>
  <a href="fooditemoffer"> Food Item Offer </a>
  
  <br/>
  <div style="width: 100%">
    <span style="float: right;">
	    API: 
	    <select id="apiSelection">
		  <option value="searchFoodItems">SearchFoodItems</option>
		  <option value="getDetailPageResults">GetDetailPageResults</option>
		  <option value="getProviderMenu">GetProviderMenu</option>
		  <option value="placeOrder">PlaceOrder</option>
		  <option value="submitOrderFeedback">SubmitOrderFeedback</option>
		  <option value="getCustomerOrder">GetCustomerOrder</option>	
		  <option value="getCustomerOrderFeedback">GetCustomerOrderFeedback</option>		  
		  	  
		</select>
	</span>
  </div>
  <br/>
  <br/>
  <span id="apiEndpoint" style="width: 100%">
  	
  </span>
  
  <table style="width: 100%; height: 480px">
   <tr>
     <td>
       <textarea id="apiInput" style="width: 100%; height: 100%; resize: vertical;"></textarea>
     </td>
     <td>
       <textarea id="apiOutput" style="width: 100%; height: 100%; resize: vertical;"></textarea>
     </td>
   </tr>
   <tr>
     <td></td>
     <td> <input type=button value=submit id="apiTrigger"/> </td>
   </tr>
  </table>
      
 </body>
</html>