document.addEventListener('DOMContentLoaded', init);

function init() {
    // TODO change counter with table id
    var counter = 0;
    var ul = document.getElementById("list");
    var input = "";
    var isNoteChecked = false;
    var data = [];

    var notesGroup = "";
    var previous = null;

    var add_btn = document.getElementById("add-btn");
    var input_btn = document.getElementById("element");
    var sort_btn = document.getElementById("sort");
    var save_btn = document.getElementById("save");
    var new_btn = document.getElementById("new-btn");

    input_btn.addEventListener('keyup', checkEnter);
    add_btn.addEventListener('click', createList);
    sort_btn.addEventListener('click', sort);
    new_btn.addEventListener('click', newGroup);

    function TodoNote(id, element, check) {
        this.id = id;
        this.element = element;
        this.check = check;
    }

	//Adds group in the list
    $.post("getList", function(responseJson) {
        var grouplist = document.getElementById('grouplist');
        $.each(responseJson, function(key, value) {
            createNewGroupLi(grouplist, value);
        });

    });

    function createNewGroupLi(grouplist, valueText){
      let li = document.createElement('li');
      let sp1 = document.createElement('span');
      let para = document.createElement('p');
      para.innerHTML = valueText;
      para.addEventListener('click', loadNotes);
      li.appendChild(sp1);
      li.appendChild(para);
      li.appendChild(getGroupDelete());
      li.addEventListener('click', checkEvent);
      grouplist.appendChild(li);
    }



    function newGroup(){
      var grouplist = document.getElementById('grouplist');
      
      var newGrpName = prompt("Please enter Group Name:", "New Group");
      let params = {
          group: newGrpName
      };
      if(!isEmpty(newGrpName)){
      	$.post("newgroup", $.param(params), function(responseJson) {
    	console.log(responseJson.result);
    		$.each(responseJson, function(key, value) {
    			if(value == 1){
    				createNewGroupLi(grouplist, newGrpName);
    			}
    		});
    	});
      }	
    }

    function loadNotes(ev) {
        notesGroup = ev.target.innerHTML;
        if(previous != null){
        	previous.classList.remove('grp-selected');
        }
        previous = ev.target;
        ev.target.classList.add('grp-selected');
        let params = {
            group: notesGroup
        };
        counter = 0;
        data = [];
        while (ul.firstChild) {
            ul.removeChild(ul.firstChild);
        }

        $.post("todos", $.param(params), function(responseJson) {
            $.each(responseJson, function(key, value) {
                input = value.note;
                isNoteChecked = value.isChecked == "1";
                addToList();
            });
        });
    }

    function sort(ev) {

        if (ev.target.sorted == false) {
            sortbyTime();
            ev.target.sorted = true;
        } else {
            sortbyName();
            ev.target.sorted = false;
        }
    }

    function checkEnter(ev) {

        input = ev.target.value;
        if (ev.keyCode == 13) {
            createList();
        }
    }

    function checkPresence(value) {
        value = value.toLowerCase();
        let present = data.some((item) => item.element.toLowerCase() == value);
        return present;
    }

    function addToList() {
        if (!checkPresence(input)) {
            let li = getNewLi();
            li.querySelector("div")
                .querySelector("p").innerHTML = input;
            insertLi(li);
        }
    }

    function createList(ev) {
        let present = checkPresence(input);

        if (input == "") {
            console.log("empty input");
        } else if (present) {
            window.alert(input + " is already in the list.");
            input_btn.focus();

        } else {
            let params = {
                group: notesGroup,
                note: input
            };
            $.post("savetodos", $.param(params), function(responseJson) {
             	$.each(responseJson, function(key, value) {
             		console.log(value);
                	if(value == 1){
                		let li = getNewLi();
                		li.querySelector("div").querySelector("p").innerHTML = input;
                		insertLi(li);
                	}
            	});
                
            });

        }
    }

    function insertLi(li) {
        data[counter] = new TodoNote(counter, input, isNoteChecked);
        isNoteChecked = false;
        ul.insertBefore(li, ul.childNodes[counter]);

        input_btn.value = "";
        input_btn.focus();

        counter++;
    }


    function getNewLi() {
        let li = document.createElement("li");
        li.id = counter;
        li.innerHTML = "<div><p></p></div>";
        li.addEventListener('click', checkEvent);
        li.appendChild(getDelete());
        li.insertBefore(getCheckBox(), li.getElementsByTagName('div')[0]);

        return li;
    }

    function checkEvent(ev) {
        if (ev.target.p == 'delete_btn') {
            deleteNote(ev);
        } else if (ev.target.p == 'check_btn') {
            togglecheck(ev);
        }else if (ev.target.p == 'delete_group_btn') {
         	 deleteGroup(ev);
        }
    }

    function deleteNote(ev) {
    	let currentTarget = ev.currentTarget;
      	let noteString = currentTarget.getElementsByTagName("p")[0].innerHTML;
        let params = {
            group: notesGroup,
            note: noteString
        };
        $.post("deletenote", $.param(params), function(responseJson) {
                		if(responseJson == '1'){
                		 	currentTarget.remove();
            				removeData(currentTarget.id);
                		}
        });
    }

    function deleteGroup(ev) {
    let currentTarget = ev.currentTarget;
      let notesGroup = currentTarget.getElementsByTagName("p")[0].innerHTML;
        let params = {
            group: notesGroup
        };
      $.post("deletegroup", $.param(params), function(responseJson) {
                		if(responseJson == "1"){
                		 	currentTarget.remove();
                		}
      });
    }

    function getCheckBox() {
        let check = document.createElement("span");
        check.p = 'check_btn';
        if (isNoteChecked) {
            check.innerHTML = "&#10004";
        } else {
            check.innerHTML = "";
        }
        check.addEventListener('click', nothing);
        return check;
    }

    function togglecheck(event) {
    	let currentTarget = event.currentTarget;
    	let target = event.target;
    	let noteString = currentTarget.getElementsByTagName("p")[0].innerHTML;
    	let checked = "n";
            if (target.innerHTML == "") {
                checked = "y";
            }
            let params = {
                group: notesGroup,
                note: noteString,
                checked: checked
            };
            $.post("checknote", $.param(params), function(responseJson) {
                		if(responseJson == '1'){
                			if (target.innerHTML == "") {
            					data[currentTarget.id].check = true;
            					target.innerHTML = "&#10004";
       						} else {
            					data[currentTarget.id].check = false;
            					target.innerHTML = "";
        					}
                		}  
            });
        
    }

    function sortbyTime() {

        data.sort((a, b) => {
            if (a.id > b.id) return 1;
            else if (a.id < b.id) return -1;
            else return 0;
        });
        changePosition();
    }

    function sortbyName() {
        data.sort((a, b) => {
            if (a.element > b.element) return 1;
            else if (a.element < b.element) return -1;
            else return 0;
        });
        changePosition();
    }

    function changePosition() {
        let i = 0;
        data.forEach((item) => {
            let li = document.getElementById(i++);
            let span = li.getElementsByTagName("span")[0];
            li.getElementsByTagName("p")[0].innerHTML = item.element;
            if (item.check == true) {
                span.innerHTML = "&#10004";
            } else {
                span.innerHTML = "";
            }
        });
        console.log("After sort", data);
    }

    function getDelete() {
        let deleteBtn = document.createElement("span");
        deleteBtn.p = "delete_btn";
        deleteBtn.innerHTML = "&#10006";
        deleteBtn.addEventListener('click', nothing);
        return deleteBtn;
    }

    function getGroupDelete() {
        let deleteBtn = document.createElement("span");
        deleteBtn.p = "delete_group_btn";
        deleteBtn.innerHTML = "&#10006";
        deleteBtn.addEventListener('click', nothing);
        return deleteBtn;
    }

    function nothing() {
        //nothing
    }

    function removeData(num) {
        data = data.filter(function(item) {
            return (item.id != num);
        });
        counter = 0;
        data.forEach((item) => {
            ul.childNodes[counter].id = counter;
            item.id = counter++;
        });
        //to remove the empty cells from the array
        console.log("after delete :", data);
    }
    
    function isEmpty(str) {
    	return (str.length === 0 || !str.trim());
	}
}
