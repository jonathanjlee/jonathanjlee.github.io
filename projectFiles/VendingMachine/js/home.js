$(document).ready(function() {
    displayItems();
    selectItem();
});

// Display the items that are for sale
function displayItems() {
    var optionRow = $('#optionRow');
    optionRow.empty();
    $.ajax({
        type: 'GET',
        url: 'http://tsg-vending.herokuapp.com/items',
        success: function(itemArray) {
            $.each(itemArray, function(index, item) {
                var itemDiv = '<div class="col-sm-3 col-xs-12 selection" onclick="selectItem(' + item.id + ', ' + item.price + ')">';
                itemDiv += '<div class="col-1 justify-content-left itemNumber">' + item.id + '</div>';
                itemDiv += '<div class="row justify-content-center align-items-center itemName">' + item.name + '</div>';
                itemDiv += '<div class="row justify-content-center itemPrice">' + formatCurrency(item.price) + '</div>';
                itemDiv += '<div class="row justify-content-center itemQty">Quanity left: ' + item.quantity + '</div>';
                itemDiv += '</div>';

                optionRow.append(itemDiv);
            })
        },
        error: function() {
            $('#displayMessage').val('Out of order.');
        }
    })
}

// Functions to add money into the system
$('#totalInserted').val('$0.00');

function addMoney(denomination) {
    $('#displayMessage').val('');
    var value = $('#totalInserted').val().replace('$', '');
    $('#totalInserted').val('$' + parseFloat(parseFloat(value) + denomination).toFixed(2));
}

// Clicking on the div will provide us with the index number of the item we are calling and provide the id for the userSelection field
var desiredItemPrice = 0.00;

function selectItem(id, price) {
    $('#userSelection').empty();
    $.ajax({
        type: 'GET',
        url: 'http://tsg-vending.herokuapp.com/items',
        success: function(item) {
            $('#userSelection').val(id);
            desiredItemPrice = price;
        },
        error: function() {
            $('#displayMessage').val('Error: Try again');
        }
    })
}

// Sends a POST to the API and will return the necessary information.
function vendItem() {
    if ($('#userSelection').val() === '') {
        $('#displayMessage').val('Please make a selection.');
        return;
    }

    var moneyDeposited = $('#totalInserted').val().replace('$', '');
    var desiredItem = $('#userSelection').val();

    $.ajax({
        type: 'POST',
        url: 'http://tsg-vending.herokuapp.com/money/' + moneyDeposited + '/item/' + desiredItem,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json',
        success: function(data) {
            $('#totalInserted').val(formatCurrency((moneyDeposited) - desiredItemPrice));
            $('#displayMessage').val('Thank You!!!');
            $('#totalChange').val(data.quarters + ' quarters, ' + data.dimes + ' dimes, ' + data.nickels + ' nickels, ' + data.pennies + ' pennies');
        },
        error: function(jqXHR, textStatus, errorThrown) {
            switch (jqXHR.status) {
                case 422:
                    $('#displayMessage').val(jqXHR.responseJSON.message);
            }
        }
    })
}

function changeReturn() {
    if (($('#totalInserted').val() > '$0.00') && ($('#userSelection').val() > 0)) {
        $('#totalInserted').val('$0.00');
        $('#displayMessage').val('');
        $('#userSelection').val('');
        $('#totalChange').val('');
    } else if (($('#totalInserted').val() > '$0.00') && ($('#userSelection').val() == '')) {
        calculateChange();
        $('#totalInserted').val('$0.00');
        $('#displayMessage').val('');
        $('#userSelection').val('');
    } else {
        $('#totalChange').val('');
        $('#displayMessage').val('');
        $('#userSelection').val('');
    }
}

// Calculates the change based on the amount that is currently inserted in the machine
function calculateChange() {
    // Remove the $ sign from the totalInserted
    var totalInsertedString = $('#totalInserted').val().replace('$', '');
    // Convert into a whole number
    var totalInserted = (totalInsertedString * 100);
    // Instantiate the coin counts
    var numberOfQuarters = 0;
    var numberOfDimes = 0;
    var numberOfNickels = 0;
    var numberOfPennies = 0;
    // Calculate the number of each coin denomination
    while (totalInserted > 0) {
        if (totalInserted >= 25) {
            numberOfQuarters = Math.floor(totalInserted / 25);
            totalInserted = totalInserted % 25;
        } else if (totalInserted >= 10) {
            numberOfDimes = Math.floor(totalInserted / 10);
            totalInserted = totalInserted % 10;
        } else {
            numberOfNickels = Math.floor(totalInserted / 5);
            numberOfPennies = totalInserted % 5;
            totalInserted = 0;
        }
    }
    $('#totalChange').val(numberOfQuarters + ' quarters, ' + numberOfDimes + ' dimes, ' + numberOfNickels + ' nickels, ' + numberOfPennies + ' pennies');
}

// Converts a String of numbers into a Float with the proper symbols
function formatCurrency(total) {
    return '$' + parseFloat(total, 10).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString();
}