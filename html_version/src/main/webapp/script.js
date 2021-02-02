function addIncome(){
    var income = prompt("Add your income");
    var div = document.getElementById("income");
    
    div.innerHTML += "<hr/><div class='row'><div class='col-6'>Paycheck 1</div><div class='col-3'>"+income+" kr</div><div class='col-3'> 0 kr</div> </div>";
    
    
}

function addExpense(){
    var expense = prompt("Add your expense");
    alert (expense);
}



