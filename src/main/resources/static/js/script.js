var survey_options = document.getElementById('survey_options');
var add_more_fields = document.getElementById('add_more_fields');
var remove_fields = document.getElementById('remove_fields');

add_more_fields.onclick = function(){


    var test2 = survey_options.children[survey_options.children.length - 1];
    var newField = document.getElementById(test2.id);
    var divClone = newField.cloneNode(true);
    var index = newField.id.substring(newField.id.length - 2, newField.id.length - 1);
    divClone.id = divClone.id.substring(0,divClone.id.length - 3) + "[" + (parseInt(index) + 1) + "]"; 
    survey_options.appendChild(divClone);
}


remove_fields.onclick = function(){

  var input_tags = survey_options.getElementsByTagName('input');
  if(input_tags.length > 10) {
    survey_options.removeChild(survey_options.lastChild);
  }
}

function getDate(){
var today = new Date();

document.getElementById("date").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);


}