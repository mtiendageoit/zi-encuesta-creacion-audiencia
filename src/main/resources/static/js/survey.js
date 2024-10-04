
// const url = 'api/encuentas/creacion-audiencia';
const url = 'https://zi-creacion-audiencia.azurewebsites.net/api/encuentas/creacion-audiencia';
$.get(url, (survey) => {
  survey.forEach(question => {
    $('#questionsContainer').append($(questionUI(question)));
  });
});

function questionUI(question) {
  const type = question.type === 'SINGLE' ? 'radio' : 'checkbox';

  const optionsUI = question.options.map(option => `
    <li>
      <input type="${type}" name="question${question.id}" id="question${question.id}Option${option.id}" option="${option.id}">
      <label for="question${question.id}Option${option.id}">
        ${option.option}
      </label>
    </li>
    `).join('');

  return `
    <div id="question${question.id}Container">
      <p><b>${question.question}</b></p>
      <ul style="list-style-type: none;" question="${question.id}">
        ${optionsUI}
        <li>
          <input type="${type}" name="question${question.id}" id="question${question.id}OptionOther">
          <label for="question${question.id}OptionOther">
            Otro (Especifique):
          </label>
          <input id="question${question.id}OptionOtherText" type="text" placeholder="Escriba su respuesta">
        </li>
      </ul>
    </div>
  `;
}

$('#sendSurvey').click(() => {
  //TODO: Validar respuestas de la encuesta

  if (!locationAddress) {
    return alert('Seleccione la ubicación de la propiedad');
  }

  const email = $('#email').val().trim();
  if (!email) {
    $('#email').select();
    return alert('Escriba un correo electrónico');
  }

  const phone = $('#phone').val().trim();
  if (phone.length > 0 && phone.length != 10) {
    $('#phone').select();
    return alert('El Whatsapp debe ser de 10 dígitos');
  }

  const answers = surveyAnswers();
  const body = {
    email: $('#email').val(),
    phone: $('#phone').val() || null,
    address: $('#address').val(),
    latitude: locationAddress.lat(),
    longitude: locationAddress.lng(),
    answers
  }

  $.post({
    url: url,
    contentType: 'application/json'
  }, JSON.stringify(body), () => {
    alert('OK. Encuesta registrada, te enviaremos tu reporte gratuito en unos instantes');
  }).fail((error) => {
    console.log(error);

    if (error.responseJSON) {
      if (error.responseJSON.code) {
        const code = error.responseJSON.code;
        if (code === 'email-already-register') {
          alert('Error: El correo electrónico ya ha sido registrado anteriormente');
        }
        return;
      }
      return alert(`Error: ${error.responseJSON.message}`);
    }

    alert('Error: El servicio no está disponible, vuelva a intentarlo más tarde.');
  });
});


function surveyAnswers() {
  let answers = [];
  $('#questionsContainer').find('ul').each((_, element) => {
    const questionId = $(element).attr('question');
    const optionIds = $(element).find('input:checked')
      .filter(function () {
        return $(this).attr('option') !== undefined;
      })
      .toArray()
      .map(input => $(input).attr('option'));

    let other = null;
    if ($(`#question${questionId}OptionOther`).is(':checked')) {
      other = $(`#question${questionId}OptionOtherText`).val();
    }

    answers.push({
      questionId: questionId,
      optionIds: optionIds,
      other: other
    });
  });

  return answers;
}


//MAP
let locationAddress = null;

async function initMap() {
  const mapCenter = { lat: 23.8757481856231, lng: -101.7511031447077 };
  const { Map } = await google.maps.importLibrary("maps");
  const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
  await google.maps.importLibrary("places");

  const map = new Map(document.getElementById("map"), {
    zoom: 5,
    center: mapCenter,
    mapId: "SURVEY_MAP_ID"
  });

  const marker = new AdvancedMarkerElement({
    map: map,
    position: mapCenter
  });

  map.addListener("click", (event) => {
    const clickedPosition = {
      lat: event.latLng.lat(),
      lng: event.latLng.lng(),
    };

    marker.position = clickedPosition;

    geocodeLocation(marker.position);
  });

  const input = document.getElementById("addressAutocomplete");
  const placeAutocomplete = new google.maps.places.Autocomplete(input, {
    componentRestrictions: { country: 'mx' }
  });

  placeAutocomplete.addListener("place_changed", () => {
    const place = placeAutocomplete.getPlace();

    if (!place.geometry || !place.geometry.location) {
      return console.error("No se encontraron detalles para la dirección seleccionada.");
    }

    if (!addressInMexico(place.address_components)) {
      return alert('La dirección no esta dentro de México');
    }

    marker.position = place.geometry.location;

    map.setCenter(marker.position);
    map.setZoom(15);

    geocodeLocation(place.geometry.location);
  });

  const geocoder = new google.maps.Geocoder();

  function geocodeLocation(location) {
    $('#address').val(null);
    locationAddress = null;
    geocoder.geocode({ location: location }, (results, status) => {
      if (status === "OK") {
        if (results[0]) {
          if (!addressInMexico(results[0].address_components)) {
            return alert('La dirección no esta dentro de México');
          }

          locationAddress = results[0].geometry.location;
          $('#address').val(results[0].formatted_address);
        }
      }
    });
  }
}

function addressInMexico(addressComponents) {
  return addressComponents.some(component => component.short_name === 'MX');
}

initMap();