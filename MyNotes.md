# Mes notes 

# init
* run l'app localement
* Lecture rapide du code java et du code front
* init git

# Correction bug 1
* Adding review does not work -> Je vois qu'il n'y a rien dans la methode updateEvent()
* Rien non plus dans le EventRepository
* En revanche on a bien l'objet event dans la methode du controller
* Ajout de la methode save()
* Je constate qu'aucune donnée ne persiste après un refresh de la page web
* Je veux regarder en base ce qui se passe -> config de hsqldb dans intelliJ impossible vu que c'est in-memory DB
* il manquait un param dans le fichier yaml : "update", pour avoir une persistence de la base (trouvé sur SO)


# Correction bug 2
* Je reproduis le bug et je constate qu'aucune donnée ne persiste après un refresh de la page web
* C'est pas un bug en front, la donnée est reset à chaque refresh de la page
* Je m'apperçois que le repository est annoté en readOnly, donc les transactions ne peuvent pas être commitée en base
* En principe, seuls les getteurs sont sensés être en readonly
* Je test mais j'ai une erreur classique d'hibernate sur une violation de contrainte, en effet il manquait les cascades

# Feature de filtre
* Pas besoin d'ajouter une route, elle existe déja, simplement le filtre n'est pas fait
* Il faut ajouter une lib de test