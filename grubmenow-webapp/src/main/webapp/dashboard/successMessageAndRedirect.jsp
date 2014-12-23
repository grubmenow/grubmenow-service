<span id="success_message_popup_info" style="color: green; text-align: center; font-size: 12px"><%= (String)request.getAttribute("message") %></span>
<script>
$("#success_message_popup_info").fadeOut(1500, function() {window.location.href = '<%= (String)request.getAttribute("redirecturl") %>'});
</script>