const writeArticleButton = document.getElementById("write-article-button");

function writeArticle() {
    location.href = "/articles/writing";
};

writeArticleButton.addEventListener("click", writeArticle);
