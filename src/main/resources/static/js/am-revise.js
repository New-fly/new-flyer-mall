function showPreview(source) {

    var file = source.files[0];

    if(window.FileReader) {

        var fr = new FileReader();

        console.log(fr);


        var portrait = document.getElementById('portrait');

        fr.onloadend = function(e) {

            portrait.src = e.target.result;

        };

        fr.readAsDataURL(file);
        portrait.style.display = 'block';
        portrait.style.width='180px';
        portrait.style.height='180px';
        portrait.style.marginLeft='85px';
        portrait.style.marginTop='85px';
        portrait.style.position='absolute';

    }

}
