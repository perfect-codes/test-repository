  //立即分享到微信朋友圈点击事件  
  $("#share_btn").on("click", function() {  
      $("#shareit").show();  
  });  
  $("#shareit").on("click", function(){
    $("#shareit").hide();   
  });
  function copy() {
      var sellerLink = document.getElementById("seller_link");
      sellerLink.select(); // 选择对象
      try {
          if(document.execCommand('copy', false, null)) {
              document.execCommand("Copy");
              $.messager.show("复制成功");
          } else {
              alert("复制失败，请手动复制");
          }
      } catch(err) {
          alert("复制失败，请手动复制");
      }
  }