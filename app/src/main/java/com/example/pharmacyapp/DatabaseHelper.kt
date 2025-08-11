package com.example.pharmacyapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import java.sql.Blob

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "final.db"
        private const val DATABASE_VERSION = 54

        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_FNAME = "fname"
        private const val COLUMN_LNAME = "lname"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_LOCATION = "location"
        private const val COLUMN_ROLE = "role"

        private const val TABLE_ARTICLES = "articles"
        private const val COLUMN_ARTICLE_ID = "id"
        private const val COLUMN_ARTICLE_TITLE = "title"
        private const val COLUMN_ARTICLE_IMAGE = "image"
        private const val COLUMN_ARTICLE_COMPANY = "company"
        private const val COLUMN_ARTICLE_DESCRIPTION = "description"

        private const val TABLE_CATEGORIES = "categorie"
        private const val COLUMN_CATGORIE_ID = "id"
        private const val COLUMN_CATEGORIE_NOM = "nom"
        private const val COLUMN_CATEGORIE_IMAGE = "image"

        private const val TABLE_MEDICAMENTS = "medicament"
        private const val COLUMN_MEDICAMENT_ID = "id"
        private const val COLUMN_MEDICAMENT_NOM = "nom"
        private const val COLUMN_MEDICAMENT_IMAGE = "image"
        private const val COLUMN_MEDICAMENT_DESC = "description"
        private const val COLUMN_MEDICAMENT_MINI_DESC = "mini_description"
        private const val COLUMN_MEDICAMENT_PRICE = "price"

        private const val TABLE_CART = "cart"
        private const val COLUMN_CART_ID = "id_cart"
        private const val COLUMN_CART_USER_ID = "id_user"
        private const val COLUMN_CART_MEDIC_ID = "id_medic"
        private const val COLUMN_CART_QUANTITY = "quantite"
        private const val COLUMN_CART_PRICE_UNITE = "prix_unite"

        private const val TABLE_COMMANDE = "commande"
        private const val COLUMN_COMMANDE_ID = "id_commande"
        private const val COLUMN_COMMANDE_USER_ID = "id_user"
        private const val COLUMN_COMMANDE_DATE = "date_commande"
        private const val COLUMN_COMMANDE_TOTAL_PRIX = "total_prix"
        private const val COLUMN_COMMANDE_STATUS = "status"


        private const val TABLE_COMMANDE_DETAILS = "commandedetails"
        private const val COLUMN_DETAIL_ID = "id_detail"
        private const val COLUMN_DETAIL_COMMANDE_ID = "id_commande"
        private const val COLUMN_DETAIL_MEDICAMENT_ID = "id_medicament"
        private const val COLUMN_DETAIL_MEDICAMENT_NOM = "nom_medicament"
        private const val COLUMN_DETAIL_MEDICAMENT_IMAGE = "image_medicament"
        private const val COLUMN_DETAIL_QUANTITE = "quantite"
        private const val COLUMN_DETAIL_PRIX = "prix_unite"

        private const val TABLE_COMMENTS = "comments"
        private const val COLUMN_COMMENT_ID = "id"
        private const val COLUMN_COMMENT_ARTICLE_ID = "article_id"
        private const val COLUMN_COMMENT_USER_NAME = "user_name"
        private const val COLUMN_COMMENT_TEXT = "text"



    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableUsers = """
        CREATE TABLE $TABLE_USERS (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_EMAIL TEXT,
            $COLUMN_PASSWORD TEXT,
            $COLUMN_FNAME TEXT,
            $COLUMN_LNAME TEXT,
            $COLUMN_PHONE INTEGER,
            $COLUMN_LOCATION TEXT,
            $COLUMN_ROLE TEXT
        )
    """.trimIndent()
        db?.execSQL(createTableUsers)

        val createTableArticles = """
         CREATE TABLE $TABLE_ARTICLES (
            $COLUMN_ARTICLE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_ARTICLE_TITLE TEXT,
            $COLUMN_ARTICLE_IMAGE INTEGER,
            $COLUMN_ARTICLE_COMPANY TEXT,
            $COLUMN_ARTICLE_DESCRIPTION TEXT
        )
    """.trimIndent()
        db?.execSQL(createTableArticles)

        val createTableCategories = """
        CREATE TABLE $TABLE_CATEGORIES (
            $COLUMN_CATGORIE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_CATEGORIE_NOM TEXT,
            $COLUMN_CATEGORIE_IMAGE INTEGER
        )
    """.trimIndent()
        db?.execSQL(createTableCategories)


        val createTableMedicaments = """
        CREATE TABLE $TABLE_MEDICAMENTS (
            $COLUMN_MEDICAMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_MEDICAMENT_NOM TEXT,
            $COLUMN_MEDICAMENT_IMAGE INTEGER,
            $COLUMN_MEDICAMENT_DESC TEXT,
            $COLUMN_MEDICAMENT_MINI_DESC TEXT,
            $COLUMN_MEDICAMENT_PRICE INTEGER,
            categorie_id INTEGER,
            FOREIGN KEY(categorie_id) REFERENCES $TABLE_CATEGORIES($COLUMN_CATGORIE_ID)
        )
    """.trimIndent()
        db?.execSQL(createTableMedicaments)

        val createTableCart = """
        CREATE TABLE $TABLE_CART (
            $COLUMN_CART_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_CART_USER_ID INTEGER,
            $COLUMN_CART_MEDIC_ID INTEGER,
            $COLUMN_CART_QUANTITY INTEGER,
            $COLUMN_CART_PRICE_UNITE INTEGER,
            FOREIGN KEY($COLUMN_CART_USER_ID) REFERENCES $TABLE_USERS($COLUMN_ID),
            FOREIGN KEY($COLUMN_CART_MEDIC_ID) REFERENCES $TABLE_MEDICAMENTS($COLUMN_MEDICAMENT_ID)
        )
    """.trimIndent()
        db?.execSQL(createTableCart)


        val createTableCommande = """
        CREATE TABLE $TABLE_COMMANDE (
            $COLUMN_COMMANDE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_COMMANDE_USER_ID INTEGER,
            $COLUMN_COMMANDE_DATE INTEGER,
            $COLUMN_COMMANDE_TOTAL_PRIX INTEGER,
            $COLUMN_COMMANDE_STATUS TEXT,
            FOREIGN KEY($COLUMN_COMMANDE_USER_ID) REFERENCES $TABLE_USERS($COLUMN_ID)
        )
    """.trimIndent()
        db?.execSQL(createTableCommande)

        val createTableCommandeDetails = """
        CREATE TABLE $TABLE_COMMANDE_DETAILS (
            $COLUMN_DETAIL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_DETAIL_COMMANDE_ID INTEGER,
            $COLUMN_DETAIL_MEDICAMENT_ID INTEGER,
            $COLUMN_DETAIL_MEDICAMENT_NOM TEXT,
            $COLUMN_DETAIL_MEDICAMENT_IMAGE INTEGER,
            $COLUMN_DETAIL_QUANTITE INTEGER,
            $COLUMN_DETAIL_PRIX INTEGER,
            FOREIGN KEY($COLUMN_DETAIL_COMMANDE_ID) REFERENCES $TABLE_COMMANDE($COLUMN_COMMANDE_ID),
            FOREIGN KEY($COLUMN_DETAIL_MEDICAMENT_ID) REFERENCES $TABLE_MEDICAMENTS($COLUMN_MEDICAMENT_ID)
        )
    """.trimIndent()
        db?.execSQL(createTableCommandeDetails)

        val createTableComments = """
        CREATE TABLE $TABLE_COMMENTS (
            $COLUMN_COMMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_COMMENT_ARTICLE_ID INTEGER,
            $COLUMN_COMMENT_USER_NAME TEXT,
            $COLUMN_COMMENT_TEXT TEXT,
            FOREIGN KEY($COLUMN_COMMENT_ARTICLE_ID) REFERENCES $TABLE_ARTICLES($COLUMN_ARTICLE_ID)
        )
    """.trimIndent()
        db?.execSQL(createTableComments)



        insertDefaultCategories(db)
        insertDefaultMedicaments(db)
        insertDefaultArticles(db)
        insertAdmin(db)

    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MEDICAMENTS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CART")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ARTICLES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_COMMANDE")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_COMMANDE_DETAILS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_COMMENTS")

        onCreate(db)
    }



    //la 1ere chose , on doit inserer un Admin

     fun insertAdmin(db: SQLiteDatabase?) {
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, "admin@example.com")
            put(COLUMN_PASSWORD, "admin123")
            put(COLUMN_FNAME, "Admin")
            put(COLUMN_LNAME, "User")
            put(COLUMN_PHONE, "123456789")
            put(COLUMN_LOCATION, "Location")
            put(COLUMN_ROLE, "admin")  //
        }
        db?.insert(TABLE_USERS, null, values)
     }


    //TODO USER FONCTS

    fun registerUser(email: String, password: String, fname: String, lname: String, phone: Int, location: String ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_FNAME, fname)
            put(COLUMN_LNAME, lname)
            put(COLUMN_PHONE, phone)
            put(COLUMN_LOCATION, location)
            put(COLUMN_ROLE, "user")  //par default
        }
        return db.insert(TABLE_USERS, null, values)
    }

    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))
        val exists = cursor.count > 0
        cursor.close()
        return exists

    }

    fun getUserRole(email: String, password: String): String {
        val db = this.readableDatabase
        val query = """
        SELECT $COLUMN_ROLE 
        FROM $TABLE_USERS 
        WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?
    """
        val cursor = db.rawQuery(query, arrayOf(email, password))
        var role = "user"  // Valeur par défaut

        if (cursor.moveToFirst()) {
            role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE))
        }

        cursor.close()
        db.close()
        return role
    }

    fun getUserIdByCredentials(email: String, password: String): Int {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_ID FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        cursor.moveToFirst()
        val userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        cursor.close()

        return userId
    }

    fun getUserById(userId: Int): User {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_ID, $COLUMN_EMAIL, $COLUMN_PASSWORD, $COLUMN_FNAME, $COLUMN_LNAME, $COLUMN_LOCATION, $COLUMN_PHONE FROM $TABLE_USERS WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
        val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
        val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FNAME))
        val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LNAME))
        val location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION))
        val phone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PHONE))

        cursor.close()

        return User(id, email, password, firstName, lastName, location, phone)
    }

    fun getAllUsers(): ArrayList<User> {
        val userList = ArrayList<User>()
        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_USERS"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FNAME))
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LNAME))
                val phone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                val location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION))

                // Créer un objet User
                val user = User(id, email, password, firstName, lastName, location, phone)
                userList.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return userList
    }




    //TODO FCTS D'INSERTION PAR DEFAULT

    fun insertDefaultCategories(db: SQLiteDatabase?) {
        val categories = listOf(
            Pair("Vitamins", R.drawable.vitamine),
            Pair("Skin care", R.drawable.medical_cat2),
            Pair("Antibiotics", R.drawable.medical_cat3),
            Pair("Calmants", R.drawable.medical_cat5),
            Pair("First Aid", R.drawable.medical_cat4)
        )

        for (category in categories) {
            val values = ContentValues().apply {
                put(COLUMN_CATEGORIE_NOM, category.first)
                put(COLUMN_CATEGORIE_IMAGE, category.second)
            }
            db?.insert(TABLE_CATEGORIES, null, values)
        }
    }

    fun insertDefaultMedicaments(db: SQLiteDatabase?) {
        val medicaments = listOf(
            Medicament(0, R.drawable.biofar5, "BIOFAR", "BIOFAR 12 Vitamins 12 Minerals is designed to fill nutritional deficiencies and strengthen the immune system. Each effervescent tablet provides a balanced intake of vitamins and minerals, helping to reduce fatigue and maintain optimal vitality.", "12 Vitamins 12 Minerals", 64, 1),
            Medicament(0, R.drawable.vit1, "MGD Nature Vitamins ", "MGD Nature offers a comprehensive supplement combining the vitamins and minerals necessary for the proper functioning of the body. Ideal for addressing nutritional deficiencies, it helps maintain energy, bone health, and overall metabolism.", "120 Capsules", 120, 1),
            Medicament(0, R.drawable.biofar1, "Biofar Vitamin C Acerola", "Biofar Vitamin C Acerola 500 offers natural vitamin C from acerola, a superfruit known for its richness in nutrients. This supplement helps protect cells from oxidative stress and supports the body's natural defenses.", "20 Tablets", 69, 1),
            Medicament(0, R.drawable.th1, "Eric Favre Vitamins ", "Eric Favre offers a synergistic combination of essential vitamins and minerals designed to enhance physical and mental performance. This supplement helps reduce fatigue, support the immune system, and optimize energy metabolism.", "30 Capsules", 112, 1),
            Medicament(0, R.drawable.aktiv, "Doppelherz Zinc + HistidinC", "Doppelherz offers a unique formula combining zinc, a crucial trace element for immunity, L-histidine, an essential amino acid, and vitamin C, a powerful antioxidant. This supplement helps protect the body against infections and maintain healthy skin.", "30 Capsules", 92, 1),
            Medicament(0, R.drawable.nutrisante, "NUTRISANTE 12 VITAMINS + ", "A comprehensive dietary supplement combining 12 vitamins and 7 trace elements to maintain energy, strengthen immunity, and reduce fatigue.", "24 Tablets", 65, 1),
            Medicament(0, R.drawable.vertufer, "VERTU PLUS IRON+ & Vitamin ", "A dietary supplement enriched with iron and vitamin C to prevent anemia, reduce fatigue, and strengthen the immune system.", "30 Capsules", 59, 1),
            Medicament(0, R.drawable.special, "SPECIAL KID CALCIUM & Vitamin D", "A tasty syrup enriched with calcium and vitamin D, ideal for supporting children's growth and the strength of bones and teeth.", "125 ml", 85, 1),
            Medicament(0, R.drawable.eucerin, "EUCERIN SUN PROTECTION CC", "Offers high sun protection against UVA and UVB rays while unifying the complexion with its medium tint. Ideal for sensitive skin, it hydrates, masks imperfections, and prevents premature aging signs. Its lightweight, non-greasy formula is perfect for daily use, providing a natural and radiant finish while effectively protecting the skin from sun damage.", "50 ml", 180, 2),
            Medicament(0, R.drawable.uriage, "Uriage Roseliane CC Cream", "Uriage Roseliane CC Cream Light Tint SPF 50+ (40 ml) protects sensitive skin from sun damage while unifying the complexion and masking redness. Enriched with thermal water, it hydrates, soothes, and provides high SPF 50+ sun protection. Ideal for skin prone to rosacea, its lightweight formula delivers lasting comfort and a natural finish.", "40 ml", 177, 2),
            Medicament(0, R.drawable.caudalie, "VinoCush Caudalie Tinted 3", "VinoCush Caudalie Tinted Cream Shade 3 (30 ml) is an anti-aging treatment that hydrates and unifies the complexion while camouflaging imperfections. Its shade 3 is suitable for medium to dark skin tones, offering a natural and radiant finish. Enriched with antioxidants, it protects the skin from aging and provides a lasting glow.", "30 ml", 395, 2),
            Medicament(0, R.drawable.caudalie2, "VinoCush Caudalie Tinted 2", "VinoCush Caudalie Tinted Cream Shade 2 (30 ml) hydrates, unifies, and protects normal to combination skin. Its shade 2 is suitable for light to medium skin tones, offering a natural and radiant finish. Enriched with antioxidants, it helps fight aging signs while providing long-lasting comfort.", "30 ml", 395, 2),
            Medicament(0, R.drawable.caudalie4, "VinoCush Caudalie Tinted 4", "VinoCush Caudalie Tinted Cream Shade 4 (30 ml) is perfect for dark skin tones. It hydrates, unifies, and protects against aging signs with antioxidants. Its lightweight texture provides a natural, radiant, and comfortable finish.", "30 ml", 395, 2),
            Medicament(0, R.drawable.fond, "8882 Anti-Tanning Foundation", "The Anti-Tanning Foundation SPF 50+ Princess (30 ml) protects the skin from sun rays while unifying the complexion. Its lightweight, hydrating formula ensures a natural, matte finish, perfect for sensitive skin looking to avoid tanning.", "30 ml", 185, 2),
            Medicament(0, R.drawable.racine, "Racine-Vita Cucumber Beauty", "Racine-Vita Cucumber Extract Beauty Care (40 ml) hydrates and soothes the skin, providing a natural glow and refreshing sensation. Perfect for sensitive skin, it tones and revitalizes the skin while leaving it soft.", "40 ml", 36, 2),
            Medicament(0, R.drawable.lactibianesachets, "Pileje Lactibiane IKI", "Pileje Lactibiane IKI (30 sachets) is a probiotic dietary supplement that helps maintain intestinal flora balance and improves digestion. It promotes digestive comfort and nutrient absorption.", "30 sachets", 450, 3),
            Medicament(0, R.drawable.microbiotiques, "DIETAROMA MICROBIOTICS FORTE", "Dietaroma Microbiotics Forte (14 sachets) is a probiotic supplement that supports digestive health and strengthens the intestinal flora balance. Ideal for improving digestion and reducing bloating, it promotes optimal intestinal comfort.", "14 sachets", 198, 3),
            Medicament(0, R.drawable.probiotiques, "PEDIAKID PROBIOTICS", "PediaKid Probiotics - 10M (10 sachets) helps maintain optimal digestion and supports intestinal flora balance in children. Easy to use, this supplement strengthens digestive comfort and the immune system.", "10 Sachets", 92, 3),
            Medicament(0, R.drawable.fortebiotic, "Forté Pharma FortéBiotic+ ATB", "Forté Pharma FortéBiotic+ ATB (10 capsules) is a probiotic supplement that helps rebalance intestinal flora after antibiotic treatment. It supports digestion and strengthens the immune system.", "10 capsules", 145, 3),
            Medicament(0, R.drawable.microbiotiquess, "DIETAROMA MICROBIOTICS INTIMATE", "Dietaroma Microbiotics Intimate Flora (20 capsules) supports the balance of female intimate flora and strengthens natural defenses. It helps maintain a healthy vaginal environment and preserve intimate comfort.", "20 capsules", 157, 3),
            Medicament(0, R.drawable.panadol, "Panadol", "Paracetamol, the active ingredient in Panadol, is known for its analgesic and antipyretic properties. It works by inhibiting the production of prostaglandins, chemicals in the body that promote inflammation, pain, and fever.", "20 film-coated tablets of 500 mg", 13, 4),
            Medicament(0, R.drawable.doliprane_removebg, "Doliprane 500 mg", "Doliprane is an analgesic and antipyretic based on paracetamol. It is used to relieve mild to moderate pain and reduce fever.", "20 tablets", 11, 4),
            Medicament(0, R.drawable.doliprane_pink, "Doliprane Pink 2.4%", "Doliprane Pink is a liquid form of paracetamol for children, specially designed for use in cases of pain and fever.", "120 ml", 25, 4),
            Medicament(0, R.drawable.codolipraneremovebg, "Codoliprane", "Codoliprane is an analgesic combining two active ingredients: paracetamol and codeine. Paracetamol works by inhibiting the production of prostaglandins, substances responsible for pain and fever. Codeine, an opioid, enhances paracetamol's analgesic effect by binding to specific receptors in the brain, reducing the perception of pain.", "16 tablets", 20, 4),
            Medicament(0, R.drawable.mepore, "Mepore Adhesive", "Non-woven, gentle on the skin, with an absorbent power.", "Sterile adhesive dressing for small wounds", 20, 5),
            Medicament(0, R.drawable.petadine, "Betadine Antiseptic Solution", "A disinfectant based on povidone iodine for disinfecting wounds.", "125 ml", 23, 5),
            Medicament(0, R.drawable.medmed, "Bepanthen Repair Cream", "Soothing and healing cream for skin irritations and small wounds.", "7.5 ml", 23, 5)
        )


        for (medicament in medicaments) {
            val values = ContentValues().apply {
                put(COLUMN_MEDICAMENT_NOM, medicament.title)
                put(COLUMN_MEDICAMENT_IMAGE, medicament.pic)
                put(COLUMN_MEDICAMENT_DESC, medicament.desc)
                put(COLUMN_MEDICAMENT_MINI_DESC, medicament.mini_desc)
                put(COLUMN_MEDICAMENT_PRICE, medicament.price)
                put("categorie_id", medicament.categoryId)
            }
            db?.insert(TABLE_MEDICAMENTS, null, values)
        }
    }


        fun insertDefaultArticles(db: SQLiteDatabase?) {
        val articles = listOf(
            Article(0, "Pounding headaches", R.drawable.article3, "www.health4.com", "Pounding headaches are intense, throbbing pains that often feel like a heavy pulse in the head. They can be caused by stress, dehydration, migraines, or underlying health conditions. These headaches may affect concentration and daily activities, lasting from a few hours to several days. Managing triggers and seeking medical advice are key to relief and prevention."),
            Article(0, "Healthy living habits", R.drawable.article2, "www.health4.com", "Healthy living habits include eating a balanced diet, staying hydrated, and exercising regularly. Getting enough sleep and managing stress are also essential for overall well-being. Avoiding tobacco and limiting alcohol intake can prevent many health issues. These habits help boost energy, strengthen immunity, and improve quality of life."),
            Article(0, "How healthy are u?", R.drawable.article5, "cleo.com.au", "How Healthy Are You? explores the key factors that determine overall well-being, from physical fitness and nutrition to mental health and lifestyle choices. The article offers insights into evaluating your current health, identifying areas for improvement, and adopting habits that promote a balanced and fulfilling life. It emphasizes the importance of regular check-ups, self-awareness, and consistency in maintaining good health."),
            Article(0, "Causes of food structs", R.drawable.article6, "www.health4.com", "Food struts, also known as food impaction, occur when food becomes lodged in the esophagus, causing discomfort or difficulty swallowing. Common causes include eating too quickly, insufficient chewing, or consuming dry or tough foods. Underlying conditions like esophageal narrowing, acid reflux, or swallowing disorders can also contribute. Identifying triggers and maintaining proper eating habits can help prevent such incidents."),
            Article(0, "Pharmacy practice", R.drawable.article7, "Helathcare newtwork.com", "Pharmacy practice encompasses the preparation, dispensing, and management of medications to ensure safe and effective patient care.Pharmacists play a critical role in advising patients on proper medication use, potential side effects, and drug interactions.They collaborate with healthcare providers to optimize treatment plans and promote health awareness."),
            Article(0, "How to eat healthy?", R.drawable.article8, "www.health4.com", "Eating healthy involves choosing nutrient-rich foods that support your body's needs and overall well-being. Focus on incorporating a variety of fruits, vegetables, whole grains, lean proteins, and healthy fats into your meals. Limit processed foods, added sugars, and excessive salt. Stay hydrated by drinking plenty of water and practice portion control to avoid overeating")
        )

        for (article in articles) {
            val values = ContentValues().apply {
                put(COLUMN_ARTICLE_TITLE, article.title)
                put(COLUMN_ARTICLE_IMAGE, article.pic)
                put(COLUMN_ARTICLE_COMPANY, article.company)
                put(COLUMN_ARTICLE_DESCRIPTION, article.desc)
            }
            db?.insert(TABLE_ARTICLES, null, values)
        }
    }


    //TODO fcts de get pour remplir rv des categories, medicaments et articles

    fun getAllCategories(): ArrayList<Categorie> {
        val db = this.readableDatabase
        val categories = ArrayList<Categorie>()

        val query = "SELECT $COLUMN_CATGORIE_ID, $COLUMN_CATEGORIE_NOM, $COLUMN_CATEGORIE_IMAGE FROM $TABLE_CATEGORIES"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATGORIE_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIE_NOM))
                val image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIE_IMAGE))
                categories.add(Categorie(id,image, name))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return categories
    }

    fun getCategorieTitleById(categoryId: Int): String {
        val db = this.readableDatabase
        var title = ""

        val query = "SELECT nom FROM categorie WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(categoryId.toString()))

        if (cursor.moveToFirst()) {
            title = cursor.getString(cursor.getColumnIndexOrThrow("nom"))
        }
        cursor.close()
        return title
    }

    fun getMedicamentsByCategory(categoryId: Int): ArrayList<Medicament> {
        val medicaments = ArrayList<Medicament>()
        val db = this.readableDatabase

        val query = """
        SELECT $COLUMN_MEDICAMENT_ID, $COLUMN_MEDICAMENT_NOM, $COLUMN_MEDICAMENT_IMAGE, 
               $COLUMN_MEDICAMENT_DESC, $COLUMN_MEDICAMENT_MINI_DESC,$COLUMN_MEDICAMENT_PRICE 
        FROM $TABLE_MEDICAMENTS
        WHERE categorie_id = ?
    """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(categoryId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MEDICAMENT_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEDICAMENT_NOM))
                val image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MEDICAMENT_IMAGE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEDICAMENT_DESC))
                val mini_description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEDICAMENT_MINI_DESC))
                val price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MEDICAMENT_PRICE))

                medicaments.add(Medicament(id, image, name, description,mini_description, price, categoryId))
            } while (cursor.moveToNext())
        }
        cursor.close()

        return medicaments
    }

    fun updateMedicamentPrice(medicamentId: Int, newPrice: Int) : Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MEDICAMENT_PRICE, newPrice)
        }

        val rowsUpdated = db.update(
            TABLE_MEDICAMENTS,
            values,
            "$COLUMN_MEDICAMENT_ID = ?",
            arrayOf(medicamentId.toString())
        )

        db.close()
        return rowsUpdated > 0
    }


    fun getAllArticles(): ArrayList<Article> {
        val db = this.readableDatabase
        val articles = ArrayList<Article>()

        val query = "SELECT * FROM $TABLE_ARTICLES"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_TITLE))
                val image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_IMAGE))
                val company = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_COMPANY))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_DESCRIPTION))

                articles.add(Article(id, title, image, company, description))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return articles
    }

    fun getArticleById(articleId: Int): Article {     //va etre supprimée
        val db = this.readableDatabase
        val query = """
        SELECT $COLUMN_ARTICLE_ID, $COLUMN_ARTICLE_TITLE, $COLUMN_ARTICLE_IMAGE, 
               $COLUMN_ARTICLE_COMPANY, $COLUMN_ARTICLE_DESCRIPTION 
        FROM $TABLE_ARTICLES
        WHERE $COLUMN_ARTICLE_ID = ?
    """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(articleId.toString()))
        cursor.moveToFirst()

        // Récupération des valeurs de l'article
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_TITLE))
        val image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_IMAGE))
        val company = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_COMPANY))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_DESCRIPTION))

        cursor.close()

        // Retourne l'objet Article
        return Article(id, title, image, company, description)
    }

    fun addComment(articleId: Int, userName: String, text: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_COMMENT_ARTICLE_ID, articleId)
        values.put(COLUMN_COMMENT_USER_NAME, userName)
        values.put(COLUMN_COMMENT_TEXT, text)
        return db.insert(TABLE_COMMENTS, null, values)
    }

    fun getCommentsByArticleId(articleId: Int): ArrayList<Comment> {
        val db = this.readableDatabase
        val comments = ArrayList<Comment>()

        // Requête pour récupérer les commentaires associés à l'article
        val commentQuery = """
            SELECT $COLUMN_COMMENT_ID, $COLUMN_COMMENT_TEXT, $COLUMN_COMMENT_USER_NAME
            FROM $TABLE_COMMENTS
            WHERE $COLUMN_COMMENT_ARTICLE_ID = ?
        """.trimIndent()

        val commentCursor = db.rawQuery(commentQuery, arrayOf(articleId.toString()))

        if (commentCursor.moveToFirst()) {
            do {
                val commentId = commentCursor.getInt(commentCursor.getColumnIndexOrThrow(COLUMN_COMMENT_ID))
                val commentText = commentCursor.getString(commentCursor.getColumnIndexOrThrow(COLUMN_COMMENT_TEXT))
                val userName = commentCursor.getString(commentCursor.getColumnIndexOrThrow(COLUMN_COMMENT_USER_NAME))

                // Création d'un objet Comment et ajout à la liste
                comments.add(Comment(commentId, articleId, userName, commentText))
            } while (commentCursor.moveToNext())
        }
        commentCursor.close()

        return comments
    }



    //TODO FCTS DE CART

    fun addToCart(userId: Int, medicId: Int, quantity: Int, priceUnite: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CART_USER_ID, userId)
            put(COLUMN_CART_MEDIC_ID, medicId)
            put(COLUMN_CART_QUANTITY, quantity)
            put(COLUMN_CART_PRICE_UNITE, priceUnite)
        }
        val newrow =  db.insert(TABLE_CART, null, values)
    }

    fun deleteCartItem(cartId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_CART, "$COLUMN_CART_ID = ?", arrayOf(cartId.toString()))
    }

    fun updateCartQuantity(cartId: Int, newQuantity: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CART_QUANTITY, newQuantity)
        }
        db.update(TABLE_CART, values, "$COLUMN_CART_ID = ?", arrayOf(cartId.toString()))
    }

    fun getCartItemsByUser(userId: Int): ArrayList<Cart> {
        val db = this.readableDatabase
        val cartItems = ArrayList<Cart>()

        val query = """
        SELECT c.$COLUMN_CART_ID, m.$COLUMN_MEDICAMENT_ID, m.$COLUMN_MEDICAMENT_NOM, 
               m.$COLUMN_MEDICAMENT_IMAGE, c.$COLUMN_CART_QUANTITY, c.$COLUMN_CART_PRICE_UNITE
        FROM $TABLE_CART c
        INNER JOIN $TABLE_MEDICAMENTS m ON c.$COLUMN_CART_MEDIC_ID = m.$COLUMN_MEDICAMENT_ID
        WHERE c.$COLUMN_CART_USER_ID = ?
    """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        if (cursor.moveToFirst()) {
            do {
                val idCart = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_ID))
                val medicId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MEDICAMENT_ID))
                val medicName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEDICAMENT_NOM))
                val medicImage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MEDICAMENT_IMAGE))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_QUANTITY))
                val price_unite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_PRICE_UNITE))

                cartItems.add(Cart(idCart, medicId, medicName, medicImage, quantity, price_unite))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cartItems
    }

    fun deleteCartItemsByUser(idUser: Int): Int {
        val db = this.writableDatabase
        // Supprimer les articles du panier où l'id_user correspond
        return db.delete(TABLE_CART, "$COLUMN_CART_USER_ID = ?", arrayOf(idUser.toString()))
    }




    //TODO  fcts de order , lorsque le client confirme la demande**//

    fun insertOrder(userId: Int, total: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id_user", userId)
        contentValues.put("date_commande", System.currentTimeMillis())
        contentValues.put("total_prix", total)
        contentValues.put("status", "ongoing")

        return db.insert("commande", null, contentValues).toInt()
    }


    fun fillCommandeDetails(idOrder: Int, idUser: Int) {
        val db = this.writableDatabase

        // Récupérer les enregistrements depuis la table `cart` pour l'utilisateur donné
        val cursor = db.query(
            TABLE_CART,
            arrayOf(COLUMN_CART_MEDIC_ID, COLUMN_CART_QUANTITY, COLUMN_CART_PRICE_UNITE),
            "$COLUMN_CART_USER_ID = ?",
            arrayOf(idUser.toString()),
            null,
            null,
            null
        )

        // Vérifier si des enregistrements existent
        if (cursor.moveToFirst()) {
            do {
                // Récupérer les colonnes nécessaires
                val medicId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_MEDIC_ID))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_QUANTITY))
                val price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_PRICE_UNITE))

                // Récupérer le nom et l'image du médicament depuis la table `medicaments`
                val medicCursor = db.query(
                    TABLE_MEDICAMENTS,
                    arrayOf(COLUMN_MEDICAMENT_NOM, COLUMN_MEDICAMENT_IMAGE),
                    "$COLUMN_MEDICAMENT_ID = ?",
                    arrayOf(medicId.toString()),
                    null,
                    null,
                    null
                )

                if (medicCursor.moveToFirst()) {
                    val medicName = medicCursor.getString(medicCursor.getColumnIndexOrThrow(COLUMN_MEDICAMENT_NOM))
                    val medicImage = medicCursor.getInt(medicCursor.getColumnIndexOrThrow(COLUMN_MEDICAMENT_IMAGE))

                    // Insérer chaque enregistrement dans `commandedetails`
                    val values = ContentValues().apply {
                        put(COLUMN_DETAIL_COMMANDE_ID, idOrder)
                        put(COLUMN_DETAIL_MEDICAMENT_ID, medicId)
                        put(COLUMN_DETAIL_MEDICAMENT_NOM, medicName)
                        put(COLUMN_DETAIL_MEDICAMENT_IMAGE, medicImage)
                        put(COLUMN_DETAIL_QUANTITE, quantity)
                        put(COLUMN_DETAIL_PRIX, price)
                    }

                    db.insert(TABLE_COMMANDE_DETAILS, null, values)
                }
                medicCursor.close()

            } while (cursor.moveToNext())
        }

        // Fermer le curseur
        cursor.close()
    }




    //TODO ****les commandes pour l'interface admin****//

    fun getOngoingOrders(): ArrayList<Order> {
        val orders = ArrayList<Order>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_COMMANDE WHERE $COLUMN_COMMANDE_STATUS = ?"
        val cursor = db.rawQuery(query, arrayOf("ongoing"))

        if (cursor.moveToFirst()) {
            do {
                val idOrder = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMMANDE_ID))
                val idUser = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMMANDE_USER_ID))
                val dateOrder = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_COMMANDE_DATE))
                val prixTotal = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMMANDE_TOTAL_PRIX))
                val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMANDE_STATUS))

                orders.add(Order(idOrder, idUser, dateOrder, prixTotal, status))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return orders
    }

    fun getOrderById(orderId: Int): Order {
        val db = this.readableDatabase

        val query = """
            SELECT 
                $COLUMN_COMMANDE_ID, 
                $COLUMN_COMMANDE_USER_ID, 
                $COLUMN_COMMANDE_DATE, 
                $COLUMN_COMMANDE_TOTAL_PRIX, 
                $COLUMN_COMMANDE_STATUS
            FROM $TABLE_COMMANDE
            WHERE $COLUMN_COMMANDE_ID = ?
        """

        val cursor = db.rawQuery(query, arrayOf(orderId.toString()))

        if (cursor.moveToFirst()) {
            val idOrder = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMMANDE_ID))
            val idUser = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMMANDE_USER_ID))
            val dateOrder = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_COMMANDE_DATE))
            val prixTotal = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMMANDE_TOTAL_PRIX))
            val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMANDE_STATUS))

            cursor.close()
            db.close()

            return Order(idOrder, idUser, dateOrder, prixTotal, status)
        } else {
            cursor.close()
            db.close()
            throw IllegalArgumentException("Commande avec ID $orderId introuvable.")
        }
    }


    fun getOrderDetails(idOrder: Int): ArrayList<OrderItem> {
        val db = this.readableDatabase
        val orderItems = ArrayList<OrderItem>()

        // Requête pour récupérer les détails de la commande en utilisant l'idOrder
        val cursor = db.query(
            TABLE_COMMANDE_DETAILS,
            arrayOf(
                COLUMN_DETAIL_ID,
                COLUMN_DETAIL_COMMANDE_ID,
                COLUMN_DETAIL_MEDICAMENT_ID,
                COLUMN_DETAIL_MEDICAMENT_NOM,
                COLUMN_DETAIL_MEDICAMENT_IMAGE,
                COLUMN_DETAIL_QUANTITE,
                COLUMN_DETAIL_PRIX
            ),
            "$COLUMN_DETAIL_COMMANDE_ID = ?",  // Condition pour l'ID de la commande
            arrayOf(idOrder.toString()),  // Paramètre de la requête
            null,
            null,
            null
        )

        // Vérifier si des résultats existent
        if (cursor.moveToFirst()) {
            do {
                // Récupérer les données à partir du curseur
                val idDetails = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DETAIL_ID))
                val medicId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DETAIL_MEDICAMENT_ID))
                val medicName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DETAIL_MEDICAMENT_NOM))
                val medicImage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DETAIL_MEDICAMENT_IMAGE))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DETAIL_QUANTITE))
                val price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DETAIL_PRIX))

                // Ajouter l'objet OrderItem à l'ArrayList
                val orderItem = OrderItem(idDetails, idOrder, medicId, medicName, medicImage, quantity, price)
                orderItems.add(orderItem)
            } while (cursor.moveToNext())
        }

        // Fermer le curseur
        cursor.close()

        // Retourner l'ArrayList des détails de la commande
        return orderItems
    }

    fun validateOrder(idOrder: Int, status: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_COMMANDE_STATUS, status) // Statut défini par le paramètre 'status'
        }

        // Mise à jour de la table commande
        val rowsAffected = db.update(
            TABLE_COMMANDE,                    // Table à mettre à jour
            values,                            // Valeurs à mettre à jour
            "$COLUMN_COMMANDE_ID = ?",         // Clause WHERE
            arrayOf(idOrder.toString())        // Paramètre de la clause WHERE
        )

        // Retourne true si au moins une ligne a été mise à jour
        return rowsAffected > 0
    }



    //TODO *****pour la reception et status******//


    @SuppressLint("Range")
    fun getOrderStatusById(orderId: Int): String {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_COMMANDE_STATUS FROM $TABLE_COMMANDE WHERE $COLUMN_COMMANDE_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(orderId.toString()))

        cursor.moveToFirst() // On est sûr que la commande existe, donc on peut directement accéder au statut
        val status = cursor.getString(cursor.getColumnIndex(COLUMN_COMMANDE_STATUS))
        cursor.close()

        return status
    }

    fun hasUserOrder(userId: Int): Boolean {
        val db = this.readableDatabase
        val query = "SELECT EXISTS (SELECT 1 FROM $TABLE_COMMANDE WHERE $COLUMN_COMMANDE_USER_ID = ?)"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        var exists = false
        if (cursor.moveToFirst()) {
            exists = cursor.getInt(0) == 1
        }
        cursor.close()
        db.close()
        return exists
    }
    fun getOrderIdByUserId(userId: Int): Int {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_COMMANDE_ID FROM $TABLE_COMMANDE WHERE $COLUMN_COMMANDE_USER_ID = ? LIMIT 1"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        cursor.moveToFirst() // Pas besoin de vérifier, car vous êtes sûr qu'une commande existe
        val orderId = cursor.getInt(0) // Récupère l'ID de la commande directement
        cursor.close()
        db.close()
        return orderId
    }

    fun getLastOrderIdByUserId(userId: Int): Int {
        val db = this.readableDatabase
        val query = """
            SELECT $COLUMN_COMMANDE_ID 
            FROM $TABLE_COMMANDE 
            WHERE $COLUMN_COMMANDE_USER_ID = ? 
            ORDER BY $COLUMN_COMMANDE_ID DESC 
            LIMIT 1
        """
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        cursor.moveToFirst() // Se déplacer directement sur la première ligne, car on suppose que des commandes existent
        val lastOrderId = cursor.getInt(0) // Récupère l'ID de la dernière commande
        cursor.close()
        db.close()
        return lastOrderId
    }

    fun getUserId(email: String): Int? {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_ID FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        val userId = if (cursor.moveToFirst()) cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)) else null
        cursor.close()
        return userId
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }

}
