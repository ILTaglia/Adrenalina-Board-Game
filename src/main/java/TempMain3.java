public class TempMain3 {
    public static void main(String []args)
    {
        Effect E;
        E=new Effect_factory().getinstanceof(1,2);
        System.out.println("L'ID sarebbe "+E.getId());
        E.adddamage(1,2);
        System.out.println("Il danno sarebbe "+E.getDamage(0).getdamage());

    }
}
