//update cart when load page

$(document).ready(() => {
    updateCartStatus();
});

function updateCartStatus() {
    cartStatusUpdater("/cart/count", {"#fullPrice": "fullPrice", "#productCount": "productCount"});
}

function cartStatusUpdater(targetUrl, updatedElements) {
    $.getJSON(targetUrl, null, (in_data) => {
        updateElements(updatedElements, in_data);
        hideIf('#shopping-item', (elem) => (parseInt(elem.find('#productCount').text()) === 0));
    });
}

function hideIf(element, condition) {
    let component = $(element);
    if (condition(component)) {
        console.log('hide element');
        component.hide();
    } else {
        console.log('show element');
        component.show();
    }
}

function updateElements(updatedElements, elementsData) {
    for (let key in updatedElements) {
        $(key).text(elementsData[updatedElements[key]])
    }
}

function initCalculate() {
    $('.calculated').each((i, element) => {

        let plus = $(element).find('.plus');
        let minus = $(element).find('.minus');
        let count = $(element).find('.count');
        let basis = $(element).find('.basis');
        let result = $(element).find('.result');

        plus.click(() => btn_click((val) => val + 1));
        minus.click(() => btn_click((val) => val - 1));

        function btn_click(calc) {
            count.val(calc(parseInt(count.val())));
            if (parseInt(count.val()) < 1) count.val(1);
            calculateResult();
        }

        function calculateResult() {
            result.text(parseFloat(basis.text()) * parseInt(count.val()));
            calcCurrentFP('#currentFP', '.result');
        }

        calculateResult();
    });
}

function calcCurrentFP(resultName, elementsName) {
    console.log('calcCurrentFP');
    $(resultName).text(sum($(elementsName)));
}

function sum(elementsScope) {
    console.log('sum');
    let result = 0;
    elementsScope.each((i, element) => {
        console.log('sum.forEach');
        result += parseFloat($(element).text());
    });
    return result;
}

