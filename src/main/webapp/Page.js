var currentStep = 1;
var updateProgressBar;

function displayStep(stepNumber) {
  if (stepNumber >= 1 && stepNumber <= 3) {
    $(".step-" + currentStep).hide();
    $(".step-" + stepNumber).show();
    currentStep = stepNumber;
    updateProgressBar();
  }
}



/// check seccond oeuvre
let nb_oeuvre = 1;
document.getElementById('buton_add').addEventListener('click', function(event) {
    document.getElementById('buton_add').classList.add('hidden');
    document.getElementById('buton_suppr').classList.remove('hidden');

    //document.getElementById('oeuvre2_simple').addEventListener('onclick',alert('hoho'));
    //div1.addEventListener('onclick',switchTab(1));

    // Rendre le champ obligatoire
    document.getElementById('OEUVRE_TITRE_2').required = true;
    document.getElementById('DIMENSIONY_2').required = true;
    document.getElementById('DimensionX_2').required = true;
    document.getElementById('prix_2').required = true;

    nb_oeuvre =2;
    switchTab(1);
    console.log(nb_oeuvre);
});
document.getElementById('buton_suppr').addEventListener('click', function(event) {
    document.getElementById('buton_suppr').classList.add('hidden');
    document.getElementById('buton_add').classList.remove('hidden');

    document.getElementById('OEUVRE_TITRE_2').required = false;
    document.getElementById('DIMENSIONY_2').required = false;
    document.getElementById('DimensionX_2').required = false;
    document.getElementById('prix_2').required = false;

    nb_oeuvre =1;
    console.log(nb_oeuvre);
});










document.getElementById('file').addEventListener('change', function(event) {
    const input = event.target;
    const fileNameElement = document.getElementById('file-name');
    
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const preview = document.getElementById('preview');
            preview.src = e.target.result;
            preview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    }

    if (input.files && input.files.length > 0) {
        const selectedFile = input.files[0];
        fileNameElement.textContent = selectedFile.name;
    } else {
        fileNameElement.textContent = 'Aucun fichier sélectionné';
    }
});

// resetButton.addEventListener('click', function() {
//     const resetButton = document.getElementById('resetButton');
//     const preview = document.getElementById('preview_2');
//     const name = document.getElementById('file-name_2');
//     const fileInput = document.getElementById('file_2');
//     fileInput.value = '';
//     name.value = "none";
//     preview.src = '';
//     preview.style.display = 'none';
// });


document.getElementById('file_2').addEventListener('change', function(event) {
    const input = event.target;
    const fileNameElement = document.getElementById('file-name_2');

    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const preview = document.getElementById('preview_2');
            preview.src = e.target.result;
            preview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    }

    if (input.files && input.files.length > 0) {
        const selectedFile = input.files[0];
        fileNameElement.textContent = selectedFile.name;
    } else {
        fileNameElement.textContent = 'Aucun fichier sélectionné';
    }
});









//check si un un chammps non required n'est pas saisie dasn une DIV (Step)
function checkRequired(divName)  {
    let formValid = '';
            const div1 = document.getElementById(divName);
            const requiredFields = div1.querySelectorAll('[required]');
            requiredFields.forEach(function(field) {
                //const errorSpan = field.nextElementSibling;
                // const errorInput = field;
                console.log(field.name)
                if (!field.value.trim()) {
                    console.log(field.name+" : vide")
                    field.classList.add('error-border');
                    formValid = field.name;
                } else {
                    field.classList.remove('error-border');
                }
            });
            if (formValid=='') {
                event.preventDefault();
                // alert('Veuillez remplir tous les champs obligatoires de la section spécifiée.');
            }
        return formValid;
}




  $(document).ready(function() {
    $('#multi-step-form').find('.step').slice(1).hide();
  
    $(".next-step").click(function() {
      let champsRequired = checkRequired("step-"+currentStep);
      console.log('champsRequired='+champsRequired)
      if (currentStep < 4 & champsRequired=='' ) {
            
            $(".step-" + currentStep).addClass("animate__animated animate__fadeOutLeft");
            currentStep++;
            setTimeout(function() {
            $(".step").removeClass("animate__animated animate__fadeOutLeft").hide();
            $(".step-" + currentStep).show().addClass("animate__animated animate__fadeInRight");
            $("progress-bar").addClass("width:" + currentStep)
            updateProgressBar();
            }, 500);
      } else  {
        if (champsRequired.endsWith("_2")) {
            //La chaîne se termine par '_2'
            switchTab(1);
        } else {
            //La chaîne ne se termine pas par '_2'
        }
      }
        
    });

    $(".prev-step").click(function() {
      if (currentStep > 1) {
        $(".step-" + currentStep).addClass("animate__animated animate__fadeOutRight");
        currentStep--;
        setTimeout(function() {
          $(".step").removeClass("animate__animated animate__fadeOutRight").hide();
          $(".step-" + currentStep).show().addClass("animate__animated animate__fadeInLeft");
          $("progress-bar").addClass("width:" + currentStep)
          updateProgressBar();
        }, 500);
      }
    });

    updateProgressBar = function() {
      var progressPercentage = ((currentStep - 1) / 3) * 100;
      $(".progress-bar").css("width", progressPercentage + "%");
    }
  });



  // swith tab
        function switchTab(tabIndex) {

            if (nb_oeuvre==1) return;
            

            // Masquer tous les contenus
            const contents = document.querySelectorAll('.tab-content');
            contents.forEach(content => content.classList.add('hidden'));
            window.scrollTo(0, 0);

            // Retirer la classe active de tous les onglets
            const tabs = document.querySelectorAll('.tab');
            tabs.forEach(tab => tab.classList.remove('active'));
            window.scrollTo(0, 0);

            // Montrer le contenu sélectionné et activer l'onglet
            contents[tabIndex].classList.remove('hidden');
            tabs[tabIndex].classList.add('active');
            window.scrollTo(0, 0);
        }


buton_suppr








const inputElements = [...document.querySelectorAll('input.code-input')]

inputElements.forEach((ele,index)=>{
  ele.addEventListener('keydown',(e)=>{
    // if the keycode is backspace & the current field is empty
    // focus the input before the current. Then the event happens
    // which will clear the "before" input box.
    if(e.keyCode === 8 && e.target.value==='') inputElements[Math.max(0,index-1)].focus()
  })
  ele.addEventListener('input',(e)=>{
    // take the first character of the input
    // this actually breaks if you input an emoji like 👨‍👩‍👧‍👦....
    // but I'm willing to overlook insane security code practices.
    const [first,...rest] = e.target.value
    e.target.value = first ?? '' // first will be undefined when backspace was entered, so set the input to ""
    const lastInputBox = index===inputElements.length-1
    const didInsertContent = first!==undefined
    if(didInsertContent && !lastInputBox) {
      // continue to input the rest of the string
      inputElements[index+1].focus()
      inputElements[index+1].value = rest.join('')
      inputElements[index+1].dispatchEvent(new Event('input'))
    }
  })
})


// mini example on how to pull the data on submit of the form
function onSubmit(e){
  e.preventDefault()
  const code = inputElements.map(({value})=>value).join('')
  console.log(code)
}