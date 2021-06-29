$(document).ready(function () {
    getCurrentWeather();
    getFiveDayForecast();
});

function clearDisplay() {
    $('#currentWeatherTitleDiv').text('');
    $('#currentWeatherTitleDiv').text('');
    $('#currentWeatherIconDiv').text('');
    $('#currentWeatherTextDiv').text('');
    $('#day1Date').text('');
    $('#day1Icon').text('');
    $('#day1Temp').text('');
    $('#day2Date').text('');
    $('#day2Icon').text('');
    $('#day2Temp').text('');
    $('#day3Date').text('');
    $('#day3Icon').text('');
    $('#day3Temp').text('');
    $('#day4Date').text('');
    $('#day4Icon').text('');
    $('#day4Temp').text('');
    $('#day5Date').text('');
    $('#day5Icon').text('');
    $('#day5Temp').text('');

}

function getCurrentWeather() {
    $('#getWeatherButton').on('click', function () {
        clearDisplay();

        var haveValidationErrors = checkAndDisplayValidationErrors($('#formInput').find('input'));

        if (haveValidationErrors) {
            return false;
        }

        $.ajax({
            type: 'GET',
            url: 'http://api.openweathermap.org/data/2.5/weather?zip=' + $('#zipCode').val() + ',us&units=' + $('#units').val() + '&appid=b8d31a18dffce8de18f70f62bac459f8',
            success: function (data) {
                $('#currentWeatherDiv').show();
                $('#currentWeatherTitleDiv').append('<p><h2>Current conditions in ' + data.name + '</h2></p>');
                $('#currentWeatherIconDiv').append('<img src="http://openweathermap.org/img/w/' + data.weather[0].icon + '.png" alt="' + data.weather[0].description + '">' + data.weather[0].main + ': ' + data.weather[0].description);
                $('#currentWeatherTextDiv').append('<p>Temperature: ' + data.main.temp + displayTempUnits() + '<br>Humidity: ' + data.main.humidity + '%<br>Wind: ' + data.wind.speed + displaySpeedUnits() + '</p>');
            },
            error: function () {
                $('#errorMessages').append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Error calling web service. Please try again later.'));
            }
        })
    })
}

function getFiveDayForecast() {
    $('#getWeatherButton').on('click', function () {
        clearDisplay();

        var haveValidationErrors = checkAndDisplayValidationErrors($('#formInput').find('input'));

        if (haveValidationErrors) {
            return false;
        }

        $.ajax({
            type: 'GET',
            url: 'http://api.openweathermap.org/data/2.5/forecast?zip=' + $('#zipCode').val() + ',us&units=' + $('#units').val() + '&cnt=5&appid=b8d31a18dffce8de18f70f62bac459f8',
            success: function (data) {
                $('#fiveDayForecastDiv').show();
                $('#day1Date').append(convertDate(data.list[0].dt));
                $('#day1Icon').append('<img src="http://openweathermap.org/img/w/' + data.list[0].weather[0].icon + '.png" alt="' + data.list[0].weather[0].description + '">' + data.list[0].weather[0].main + ': ' + data.list[0].weather[0].description);
                $('#day1Temp').append('<p>H: ' + data.list[0].main.temp_max + displayTempUnits() + '<br>L: ' + data.list[0].main.temp_min + displayTempUnits() + '</p>');
                $('#day2Date').append(convertDate(data.list[1].dt));
                $('#day2Icon').append('<img src="http://openweathermap.org/img/w/' + data.list[1].weather[0].icon + '.png" alt="' + data.list[1].weather[0].description + '">' + data.list[1].weather[0].main + ': ' + data.list[1].weather[0].description);
                $('#day2Temp').append('<p>H: ' + data.list[1].main.temp_max + displayTempUnits() + '<br>L: ' + data.list[1].main.temp_min + displayTempUnits() + '</p>');
                $('#day3Date').append(convertDate(data.list[2].dt));
                $('#day3Icon').append('<img src="http://openweathermap.org/img/w/' + data.list[2].weather[0].icon + '.png" alt="' + data.list[2].weather[0].description + '">' + data.list[2].weather[0].main + ': ' + data.list[2].weather[0].description);
                $('#day3Temp').append('<p>H: ' + data.list[2].main.temp_max + displayTempUnits() + '<br>L: ' + data.list[2].main.temp_min + displayTempUnits() + '</p>');
                $('#day4Date').append(convertDate(data.list[3].dt));
                $('#day4Icon').append('<img src="http://openweathermap.org/img/w/' + data.list[3].weather[0].icon + '.png" alt="' + data.list[3].weather[0].description + '">' + data.list[3].weather[0].main + ': ' + data.list[3].weather[0].description);
                $('#day4Temp').append('<p>H: ' + data.list[3].main.temp_max + displayTempUnits() + '<br>L: ' + data.list[3].main.temp_min + displayTempUnits() + '</p>');
                $('#day5Date').append(convertDate(data.list[4].dt));
                $('#day5Icon').append('<img src="http://openweathermap.org/img/w/' + data.list[4].weather[0].icon + '.png" alt="' + data.list[4].weather[0].description + '">' + data.list[4].weather[0].main + ': ' + data.list[4].weather[0].description);
                $('#day5Temp').append('<p>H: ' + data.list[4].main.temp_max + displayTempUnits() + '<br>L: ' + data.list[4].main.temp_min + displayTempUnits() + '</p>');
            },
            error: function () {
                $('#errorMessages');
            }
        })
    })
}

function convertDate(unixDateTimeStamp) {
    var months_arr = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    var date = new Date(unixDateTimeStamp * 1000);
    var year = date.getFullYear();
    var month = months_arr[date.getMonth()];
    var day = date.getDate();
    var hours = date.getHours();
    var minutes = "0" + date.getMinutes();
    var seconds = "0" + date.getSeconds();
    var formatDate = month + ' ' + day + ', ' + year;
    return formatDate;
}

function displayTempUnits() {
    switch ($('#units').val()) {
        case "imperial":
            return "&degF";
            break;
        case "metric":
            return "&degC";
            break;
        default:
            return "unknown units"
    }
}

function displaySpeedUnits() {
    switch ($('#units').val()) {
        case "imperial":
            return " miles/hour";
            break;
        case "metric":
            return " km/hour";
            break;
        default:
            return " unknown units"
    }
}

function checkAndDisplayValidationErrors(input) {
    $('#errorMessages').empty();

    var errorMessages = [];

    input.each(function () {
        if (!this.validity.valid) {
            var errorField = $('label[for=' + this.id + ']').text();
            errorMessages.push(errorField + ' ' + this.validationMessage);
        }
    });

    if (errorMessages.length > 0) {
        $.each(errorMessages, function (index, message) {
            $('#errorMessages').append($('<li>').attr({ class: 'list-group-item list-group-item-danger' }).text(message));
        });
        return true;
    } else {
        return false;
    }
}
