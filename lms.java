public class lms {
    public class person
    {
        String name;
        int age;
        void details(String name,int age){
            this.name=name;
            this.age=age;
        }
    }
    public class student extends person{
        String id;
        boolean issued;
        void stinfo(String id,boolean issued)
        {
            this.id=id;
            this.issued=issued;
        }
    }
    public class librarian extends person{
        String id;
        void stinfo(String id)
        {
            this.id=id;
        }
    }
    public class operations{
        void issue(String name,String id)
        {
        }
    }
    public class book{
        String name;
        String AuthorName;
        int Amount;
        boolean available;
        void Binfo(String name, String AuthorName,int Amount, boolean available)
        {
            this.name=name;
            this.AuthorName=AuthorName;
            this.Amount=Amount;
            this.available=available;
        }
    }
}
