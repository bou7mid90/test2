function changeDownload($scope, $location) {
  
}

function catsel(sel) {

    //if (sel.value=="-1" ) return;

    var opt=sel.getElementsByTagName("option" );

    for (var i=0; i<opt.length; i++) {

      var x=document.getElementById(opt[i].value);

      if (x) x.style.display="none";

    }

    var cat = document.getElementById(sel.value);
    var doc = document.getElementById("f");
    if (doc) doc.style.display="block";
    if (cat) cat.style.display="block";
    

  }