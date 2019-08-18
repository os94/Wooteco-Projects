

const saveButton = document.getElementById("save-button");

const save = function () {
    let file = document.getElementById("file").value;

    if (file === '') {
        alert("파일은 필수입니다");
        return;
    }

    let postForm = document.getElementById("saveForm");
    let formData  = new FormData(postForm);

    fetch ('/api/articles', {
        method: "POST",
        body: formData
    }).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            alert('게시글 작성에 실패했습니다.');
        }
    }).then(article => {
        window.location.href = '/articles/' + article.id;
    })
}

saveButton.addEventListener('click',save);
