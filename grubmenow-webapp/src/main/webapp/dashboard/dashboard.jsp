<html>
 <head> 
  <jsp:include page="common.jsp" />
  
   <script>
	$(document).ready(function() {
		
		$('#apiSelection').change(function(e) {
			var obj = new Object();
			var selectedAPI = $("#apiSelection option:selected").val();
			
			$('#apiEndpoint').text('/' + selectedAPI);				
			
			if(selectedAPI == 'searchFoodItems') {
				obj.zipCode = '98007';
				obj.availableDay = 'TODAY';
				obj.radius = 10;
			}
			
			if(selectedAPI == 'getDetailPageResults') {
				obj.foodItemId = '225636';
				obj.availableDay = 'TODAY';
				obj.zipCode = '98007';
				obj.radius = 10;
			}
			
			if(selectedAPI == 'getProviderMenu') {
				obj.providerId = 'ProviderId1';
				obj.availableDay = 'TODAY';
			}

			$('#apiInput').text(JSON.stringify(obj, undefined, 2));
			$('#apiOutput').text('');
		});
		
		$('#apiSelection').trigger("change");
		
   		$('#apiTrigger').click(function(e) {
   		  
   		  $('#apiOutput').text('Processing..');
   		  var inputData = $('#apiInput').val();
		  var selectedAPI = $("#apiSelection option:selected").val();	
   		  var apiURL = '../' + selectedAPI;
		  
		  $.ajax({
   		    type: "POST",
   			dataType: "json",
   			contentType: "application/json",
   			url: apiURL,
   			data: inputData, 
   			success: function(res) {
   						$('#apiOutput').text(JSON.stringify(res, undefined, 2));
   					},
   			error:  function(res) {
						$('#apiOutput').text(JSON.stringify(res, undefined, 2));
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