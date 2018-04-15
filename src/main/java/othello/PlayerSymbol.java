package othello;

enum PlayerSymbol {


    X('X'),
    O('O'),
    Neither('-');

    private final char presentation;

    PlayerSymbol(char presentation) {
        this.presentation = presentation;
    }
    public String toString() {
       return  "" + this.getPresentation();
    }
    public char getPresentation() {
        return this.presentation;
    }
    public PlayerSymbol oppositePlayer() {
        if (this == X) {
            return O;
        } else if (this == O) {
            return X;
        } else {
            return Neither;
        }
    }


}
