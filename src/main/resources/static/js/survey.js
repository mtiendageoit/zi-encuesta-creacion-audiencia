
const url = 'api/encuentas/creacion-audiencia';
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
  const answers = surveyAnswers();
  const body = {
    email: $('#email').val(),
    phone: $('#phone').val(),
    address: $('#address').val(),
    answers
  }

  //TODO: Validar respuestas y datos a enviar

  $.post({
    url: url,
    contentType: 'application/json'
  }, JSON.stringify(body), () => {
    alert('Correcto. Encuesta registrada, te enviaremos tu reporte gratuito en unos instantes');
  }).fail((error) => {
    if (error.responseJSON) {
      const code = error.responseJSON.code;
      if (code === 'email-already-register') {
        alert('Error: El correo electrónico ya ha sido registrado anteriormente');
      }
      return;
    }
    alert('El servicio de registro no está disponible, vuelva a intentarlo más tarde.');
    console.log(error);
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