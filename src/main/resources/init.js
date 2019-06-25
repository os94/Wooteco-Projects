
function create(div) {

    var block=['bishopBlack','kingBlack','knightBlack','pawnBlack','queenBlack','rookBlack'];
    var white=['bishopWhite','kingWhite','knightWhite','pawnWhite','queenWhite','rookWhite'];


    const id=div.id;

    if(id>=9 && id<=16)     // 9~16 pown
    {
        div.classList.add(block[3]);
    }

    if(id==1 || id==8)      // rook
    {
        div.classList.add(block[5]);
    }
    if(id==2 || id==7)      // knight
    {
        div.classList.add(block[2])
    }
    if(id==3 || id==6)  // bishiop
    {
        div.classList.add(block[0])
    }
    if(id==4)       // king
    {
        div.classList.add(block[1])
    }
    if(id==5)       // queen
    {
        div.classList.add(block[4])
    }


    if(id>=49 && id<=56)        // 49~54 pown
    {
        div.classList.add(white[3]);
    }
    if(id==60)       // king
    {
        div.classList.add(white[1])
    }
    if(id==61)       // queen
    {
        div.classList.add(white[4])
    }
    if(id==59 || id==62)
    {
        div.classList.add(white[1]);
    }

    if(id==58 || id==63)
    {
        div.classList.add(white[2])
    }
    if(id==57 || id==64)
    {
        div.classList.add(white[5]);
    }


}


function init() {

    const checkerBoard = document.getElementById("checker-board");
    var done=false;
    var white=false;

    for(var i=1; i<=64; i++)
    {
        const div = document.createElement("div");
        div.id=i+"";

        if(white)  // 짝수면
        {
            div.classList.add('light','square');
        }
        if(!white)
        {
            div.classList.add('dark','square');
        }

        create(div);

        white=!white

        if(i%8==0)
        {
            white=!white;
            done=!done;
        }

        checkerBoard.append(div);
    }
}

init();

