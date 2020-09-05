$(document).ready(function() {

	$('#gamefile_file').on('change', function(){
		if(window.FileReader){
			var filename = $(this)[0].files[0].name;
		} else {
			var filename = $(this).val().split('/').pop().split('\\').pop();
		}
		$('#exampleInputGame').val(filename);
	});

	$('#thumbfile_file').on('change', function(){
		if(window.FileReader){
			var filename = $(this)[0].files[0].name;
		} else {
			var filename = $(this).val().split('/').pop().split('\\').pop();
		}
		$('#exampleInputThumbnail').val(filename);
	});
});