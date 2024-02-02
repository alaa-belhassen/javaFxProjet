package tn.esprit.javafxproject.models;

public class Emoji {
    private int idEmoji;
    private String nomEmoji;
    private int rank;
    private String imageUrl;
    private int prix;
    private String Status;

    public Emoji() {
    }

    public Emoji( String nomEmoji, int rank, String imageUrl,int prix) {
        this.nomEmoji = nomEmoji;
        this.rank = rank;
        this.imageUrl = imageUrl;
        this.prix = prix;
    }

    public Emoji(Emoji emoji) {
        this.idEmoji = emoji.getIdEmoji();
        this.nomEmoji = emoji.getNomEmoji();
        this.rank = emoji.getRank();
        this.imageUrl = emoji.getImageUrl();
        this.prix = emoji.getPrix();
        Status = emoji.getStatus();
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getIdEmoji() {
        return idEmoji;
    }

    public void setIdEmoji(int idEmoji) {
        this.idEmoji = idEmoji;
    }

    public String getNomEmoji() {
        return nomEmoji;
    }

    public void setNomEmoji(String nomEmoji) {
        this.nomEmoji = nomEmoji;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Emoji{" +
                "idEmoji=" + idEmoji +
                ", nomEmoji='" + nomEmoji + '\'' +
                ", rank=" + rank +
                ", imageUrl='" + imageUrl + '\'' +
                ", prix=" + prix +
                '}';
    }
}
